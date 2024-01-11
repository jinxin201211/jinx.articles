package com.jinx.test.controller;

import com.jinx.test.multiple.service.IGreenSearchAlgorithmService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 绿证查找算法表 前端控制器
 * </p>
 *
 * @author jinxin
 * @since 2024-01-11
 */
@RestController
@RequestMapping("/multiple/master")
@Api(tags = "多数据源-主库")
public class GreenSearchAlgorithmController {
    @Autowired
    private IGreenSearchAlgorithmService searchAlgorithmService;

    @GetMapping("/list")
    public Object list() {
        return searchAlgorithmService.list();
    }
}
