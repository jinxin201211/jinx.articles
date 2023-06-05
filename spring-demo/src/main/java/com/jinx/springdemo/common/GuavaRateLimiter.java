package com.jinx.springdemo.common;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

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
