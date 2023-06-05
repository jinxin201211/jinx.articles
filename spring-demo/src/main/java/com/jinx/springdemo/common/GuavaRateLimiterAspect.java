package com.jinx.springdemo.common;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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