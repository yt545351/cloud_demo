package com.example.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * 封装接口文档信息
     * swagger会帮组我们生成文档
     * 1. 配置生成的文档信息
     * 2. 配置生成的规则
     *
     * @return {@link Docket}
     */
    @Bean
    public Docket getDocket() {

        // 创建封面信息对象
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        // 设置文档标题、描述、联系人
        apiInfoBuilder.title("平台接口说明文档")
                .description("此文档说明了平台后端接口规范")
                .version("v 1.0.0")
                .contact(new Contact("yt", "www.baidu.com", "545351067@qq.com"));

        ApiInfo apiInfo = apiInfoBuilder.build();

        // 指定文档风格
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                // 指定生成的文档中的封面信息；文档标题、版本、作者
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }
}
