package com.kb._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)  // 기존적인 응답메시지 미사용
                .globalResponseMessage(RequestMethod.POST, getArrayList()) // 정의한 응답메시지 사용
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kb"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("게시판 REST API Documentation",
                "REST Api Documentation",
                "1.0",
                "localhost:8080",
                new Contact("myhome","http://myhome.co.kr","test@test.email"),
                "마이시스템", "http://myhome.co.kr",
                new ArrayList<VendorExtension>());
    }

    private ArrayList<ResponseMessage> getArrayList() {
        ArrayList<ResponseMessage> lists = new ArrayList<ResponseMessage>();
        lists.add(new ResponseMessageBuilder().code(500).message("500 ERROR").build());
        lists.add(new ResponseMessageBuilder().code(403).message("403 ERROR").build());
        lists.add(new ResponseMessageBuilder().code(401).message("401 ERROR").build());
        return lists;
    }

    @Bean
    public UiConfiguration uiconfig() {
        return UiConfigurationBuilder
                .builder().operationsSorter(OperationsSorter.ALPHA)
                .build();
    }

}
