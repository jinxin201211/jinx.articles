package com.jinx.springdemo.controller;

import com.jinx.springdemo.common.GuavaRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @GetMapping("/rateLimiter")
    @GuavaRateLimiter(value = 1, timeout = 200)
    public String rateLimiter() {
        return "success";
    }
}