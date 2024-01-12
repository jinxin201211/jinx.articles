# SpringBoot 整合 JWT 实现分布式应用登录校验

## JWT 入门介绍

官网地址：[jwt.io](https://jwt.io)

## 什么是 JWT？

JSON Web 令牌（JWT）是一个开放标准（[RFC 7519](https://tools.ietf.org/html/rfc7519)），它定义了一种紧凑且自包含的方式，用于在各方之间安全地传输信息作为 JSON 对象。由于此信息是经过数字签名的，因此可以被验证和信任。可以使用秘密（使用 HMAC 算法）或使用 RSA 或 ECDSA 的公钥/私钥对对 JWT 进行签名。

尽管可以对 JWT 进行加密以提供双方之间的保密性，但我们将重点关注已签名的令牌。签名的令牌可以验证其中包含的声明的完整性，而加密的令牌则将这些声明隐藏在其他方的面前。当使用公钥/私钥对对令牌进行签名时，签名还证明只有持有私钥的一方才是对其进行签名的一方。

## JWT 组成部分

header+payload+signature

- 头部：主要是描述签名算法
- 负载：主要描述是加密对象的信息，如用户的 id 等，也可以加些规范里面的东西，如 iss 签发者，exp 过期时间，sub 面向的用户
- 签名：主要是把前面两部分进行加密，防止别人拿到 token 进行 base 解密后篡改 token

## JWT 优缺点

- 优点
  - 生产的 token 可以包含基本信息，比如 id、用户昵称、头像等信息，避免再次查库
  - 存储在客户端，不占用服务端的内存资源
- 缺点
  - token 是经过 base64 编码，所以可以解码，因此 token 加密前的对象不应该包含敏感信息，如用户权限，密码等
  - 如果没有服务端存储，则不能做登录失效处理，除非服务端改秘钥

## SpringBoot 整合 JWT

### 1、添加相关依赖、

``` xml
<!-- JWT 相关 -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.7.0</version>
</dependency>
```

### 2、封装 JWT Utils 工具类

封装生成 token 方法

``` java
public static String geneJsonWebToken(LoginUser loginUser) {
    if (loginUser == null) {
        throw new NullPointerException("loginUser 对象为空");
    }
    Long userId = loginUser.getId();
    String token = Jwts.builder().setSubject(SUBJECT)
        .claim("head_img", loginUser.getHeadImg())
        .claim("id", loginUser.getId())
        .claim("name", loginUser.getName())
        .claim("mail", loginUser.getMail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
        .signWith(SignatureAlgorithm.HS256, SECRET).compact();

    token = TOKEN_PREFIX + token;
    return token;
}
```

封装解析 token 方法

``` java
public static Claims checkJwt(String token){
    try {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            .getBody();
        return claims;
    } catch (Exception e) {
        log.info("jwt 解密失败，token={}",token,e.getMessage());
    }
    return null;
}
```

## JWT 相关问题

### JWT 过期自动刷新方案介绍

背景

- 在前后分离场景下，越来越多的项目使用 jwt token 作为接口的安全机制，但存在 jwt 过期后，用户无法直接感知，假如在用户操作页面期间，突然提示登录，则体验很不友好，所以就有了 token 自动刷新需求

  但是这个自动刷新方案，基本都离不开服务端状态存储，JWT 推出思想是：去中心化，无状态化，所以有所违背。类似这样的业务，有阿里云首页，没有做 token 刷新令牌维护，但是符合对应的思想

解决方案

```text
用户登录成功的时候，一次性给他两个 Token，分别为 AccessToken 和 RefreshToken
AccessToken 有效期较短，比如 1 天或者 5 天，用于正常请求
RefreshToken 有效期可以设置长一些，例如 10 天、20 天，作为刷新 AccessToken 的凭证

刷新方案：当 AccessToken 即将过期的时候，例如提前 30 分钟，客户端利用 RefreshToken 请求指定的 API 获取新的 AccessToken 并更新本地存储中的 AccessToken

核心逻辑
1、登录成功后，jwt 生成 AccessToken； UUID 生成 RefreshToken 并存储在服务端 redis 中，设置过期时间
2、接口返回 3 个字段 AccessToken/RefreshToken/访问令牌过期时间戳
3、由于 RefreshToken 存储在服务端 redis 中，假如这个 RefreshToken 也过期，则提示重新登录； 

老王的疑问：RefreshToken 有效期那么长，和直接将 AccessToken 的有效期延长有什么区别

答：RefreshToken 不像 AccessToken 那样在大多数请求中都被使用，主要是本地检测 accessToken 快过期的时候才使用，
一般本地存储的时候，也不叫 refreshToken, 前端可以取个别名，混淆代码让攻击者不能直接识别这个就是刷新令牌

缺点：前端每次请求需要判断 token 距离过期时间
优点：后端压力小，代码逻辑改动不大
```

### JWT 令牌 token 泄露恶意使用解决方案

解密：使用互联网大厂的产品时经常遇到这个情况

- 比如阿里云或者淘宝，你现在登录了然后换个网络或者地域就需要重新登录

- 就是对应的 token 令牌，不只简单的算法加密，还包括了客户端属性、地理网络位置信息等，一起组成一个 token 令牌

如何避免 token 令牌泄露被恶意使用

- ip 绑定方式

- 绑定浏览器 user-agent

解决方案

```text
生成 token 的时候，加密的 payload 加入当前用户 ip。

拦截器解密后，获取 payload 的 ip 和当前访问 ip 判断是否同个，如果不是则提示重新登录

优点：服务端无需存储相关内容，性能高，假如用户广州登录，泄露了 token 给杭州的黑客，依旧用不了

缺点：如果用户用使用过程中 ip 变动频繁，则操作会经常提示重新登录，体验不友好

当然也可以让用户开启安全模式和非安全模式，让用户自己知道这个情况，一些区块链、比特币交易所里面就会让用户自己选择控制这个 token 令牌安全是否和 ip、终端、地理网络信息进行绑定
```
