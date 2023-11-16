package com.jinx.springdemo.controller;

import com.jinx.springdemo.common.GuavaRateLimiter;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import javassist.*;
import javassist.util.HotSwapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Value("${server.port:8080}")
    private Integer serverPort;

    @GetMapping("/rateLimiter")
    @GuavaRateLimiter(value = 1, timeout = 200)
    public String rateLimiter() {
        return "success";
    }

    @GetMapping("/testInstance")
    public String testInstance() {
        TestTool testTool = new TestTool();
        testTool.print();
        return "success";
    }

    @GetMapping("/changeInstance")
    public String changeInstance() {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.getCtClass("com.jinx.springdemo.controller.TestTool");
            CtMethod ctMethod = ctClass.getDeclaredMethod("print");
            ctMethod.setBody("{System.out.println(\"hello world\");}");
            ctClass.writeFile();

            HotSwapper hotSwapper = new HotSwapper(serverPort);
            hotSwapper.reload(ctClass.getName(), ctClass.toBytecode());
            ctClass.defrost();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalConnectorArgumentsException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, IllegalConnectorArgumentsException {
        TestTool testTool = new TestTool();
        testTool.print();

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass("com.jinx.springdemo.controller.TestTool");
        CtMethod ctMethod = ctClass.getDeclaredMethod("print");
        ctMethod.setBody("{System.out.println(\"hello world\");}");
        ctClass.writeFile();

        HotSwapper hotSwapper = new HotSwapper(8080);
        hotSwapper.reload(ctClass.getName(), ctClass.toBytecode());
    }
}