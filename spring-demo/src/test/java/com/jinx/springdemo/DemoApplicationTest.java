package com.jinx.springdemo;

import com.jinx.springdemo.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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
