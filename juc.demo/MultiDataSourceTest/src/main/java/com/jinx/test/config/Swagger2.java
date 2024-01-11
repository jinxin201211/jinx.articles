package com.jinx.test.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description: Swagger2插件配置类
 * @Author: lsh
 * @Date: 2021/4/7 9:04
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {

    // 定义分隔符,配置Swagger多包
    private static final String splitor = ";";

    /**
     * @description: 文档@ApiOperation注解来给API增加说明、 通过@ApiImplicitParams、@ApiImplicitParam注解来给参数增加说明。
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("测试内网")
                .apiInfo(apiInfo()).enable(true).select()  //false 禁用  true开启
                .apis(basePackage("com.jinx.test.controller"))
                .paths(PathSelectors.any()).build();
//				.globalOperationParameters(setHeaderToken());
    }

    /**
     * @description: 版本信息备注
     */
    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("NW API")
                .description("可再生绿证测试内网接口文档")
                .version("1.0").build();
    }

    /**
     * @param basePackage 包路径
     * @description: 重新组装包路径
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    /**
     * @param basePackage 包路径
     * @description: 处理包路径配置规则, 支持多路径扫描匹配以自定义符号隔开
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * @param input
     * @description: 声明类方法
     */
    @SuppressWarnings("deprecation")
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}
