package com.jinx.test.controller;

import com.jinx.test.multiple.service.IAcloudConfigRepoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 配置仓库 前端控制器
 * </p>
 *
 * @author jinxin
 * @since 2024-01-11
 */
@RestController
@RequestMapping("/multiple/slavor")
@Api(tags = "多数据源-副库")
public class AcloudConfigRepoController {
    @Autowired
    private IAcloudConfigRepoService configRepoService;

    @GetMapping("/list")
    public Object list() {
        return configRepoService.list();
    }
}
