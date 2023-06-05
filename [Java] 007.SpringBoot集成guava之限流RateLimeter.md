## <center>**SpringBoot集成guava之限流RateLimeter**</center>

**1. 前言**

Guava工程包含了若干被Google的 Java项目广泛依赖 的核心库，例如：集合 [collections] 、缓存 [caching] 、原生类型支持 [primitives support] 、并发库 [concurrency libraries] 、通用注解 [common annotations] 、字符串处理 [string processing] 、I/O 等等。

**2. springBoot集成rateLimeter实现单机限流**

**2.1. 引入guava依赖**

``` xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>30.1.1-jre</version>
</dependency>
```

**2.2. 自定义限流注解**

``` java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GuavaRateLimiter {
    int NOT_LIMITED = 0;

    /**
     * qps：Queries-per-second， 每秒查询率，QPS = req/sec = 请求数/秒
     * 每秒生成令牌的个数
     */
    @AliasFor("qps") double value() default NOT_LIMITED;

    /**
     * qps
     */
    @AliasFor("value") double qps() default NOT_LIMITED;

    /**
     * 获取令牌的超时时长
     */
    int timeout() default 0;

    /**
     * 超时时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}

```

**2.3. 定义限流切面**

``` java
@Slf4j
@Aspect
@Component
public class GuavaRateLimiterAspect {
    private static final ConcurrentMap<String, RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.jinx.springdemo.common.GuavaRateLimiter)")
    public void rateLimit() {
    }

    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 通过 AnnotationUtils.findAnnotation 获取 GuavaRateLimiter 注解
        GuavaRateLimiter guawaRateLimiter = AnnotationUtils.findAnnotation(method, GuavaRateLimiter.class);
        if (guawaRateLimiter != null && guawaRateLimiter.qps() > GuavaRateLimiter.NOT_LIMITED) {
            double qps = guawaRateLimiter.qps();
            if (RATE_LIMITER_CACHE.get(method.getName()) == null) {
                // 初始化 QPS
                RATE_LIMITER_CACHE.put(method.getName(), RateLimiter.create(qps));
            }
            String methodName = method.getDeclaringClass().getCanonicalName() + "." + method.getName();
            log.debug("【{}】的QPS设置为: {}", methodName, RATE_LIMITER_CACHE.get(method.getName()).getRate());
            // 尝试获取令牌
            if (RATE_LIMITER_CACHE.get(method.getName()) != null && !RATE_LIMITER_CACHE.get(method.getName()).tryAcquire(guawaRateLimiter.timeout(), guawaRateLimiter.timeUnit())) {
                log.warn("【{}】被限流啦", methodName);
                return "too many request";
            }
        }
        return point.proceed();
    }
}
```

**2.4. 定义Controller进行测试**

```java
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @GetMapping("/rateLimiter")
    @GuavaRateLimiter(value = 2, timeout = 0)
    public String rateLimiter() {
        return "success";
    }
}
```

**2.5. 测试**

可以看到当我们连续点击的时候 会被限流,就这样单机限流配置成功.
 
[总结]:集成guava实现限流,简单,方便,便于理解. 不足是在单机版的限流比较局限,有的时候没法适合业务,所以在选择上我们要根据具体的业务场景去选择