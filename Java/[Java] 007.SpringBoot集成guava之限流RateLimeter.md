# $SpringBoot集成guava之限流RateLimeter$

## 1. 前言

Guava工程包含了若干被Google的 Java项目广泛依赖 的核心库，例如：集合 [collections] 、缓存 [caching] 、原生类型支持 [primitives support] 、并发库 [concurrency libraries] 、通用注解 [common annotations] 、字符串处理 [string processing] 、I/O 等等。

## 2. springBoot集成rateLimeter实现单机限流

### 2.1. 引入guava依赖

``` xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>30.1.1-jre</version>
</dependency>
```

### 2.2. 自定义限流注解

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

### 2.3. 定义限流切面

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

### 2.4. 定义Controller进行测试

```java
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @GetMapping("/rateLimiter")
    @GuavaRateLimiter(value = 1, timeout = 100)
    public String rateLimiter() {
        return "success";
    }
}
```

### 2.5. 测试

测试代码

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoApplicationTest {
    @Resource
    private TestController testController;

    @Test
    public void testLimiter() throws InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        for (int i = 0; i < 10; i++) {
            System.out.println("[" + dateFormat.format(new Date()) + "] " + i + ": " + testController.rateLimiter());
            Thread.sleep(200l);
        }
    }
}
```

测试配置`@GuavaRateLimiter(value = 1, timeout = 0)`，结果：

![CountDownLatch](/imgs/ratelimiter/Snipaste_2023-06-05_17-22-03.jpg)

测试配置`@GuavaRateLimiter(value = 1, timeout = 200)`，结果：

![CountDownLatch](/imgs/ratelimiter/Snipaste_2023-06-05_17-23-03.jpg)

测试配置`@GuavaRateLimiter(value = 1, timeout = 500)`，结果：

![CountDownLatch](/imgs/ratelimiter/Snipaste_2023-06-05_17-23-32.jpg)

测试配置`@GuavaRateLimiter(value = 1, timeout = 1000)`，结果：

![CountDownLatch](/imgs/ratelimiter/Snipaste_2023-06-05_17-24-06.jpg)
