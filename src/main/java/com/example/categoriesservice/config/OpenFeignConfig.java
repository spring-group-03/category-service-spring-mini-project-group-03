package com.example.categoriesservice.config;

import com.example.categoriesservice.exception.AuthHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + AuthHelper.getCurrentTokenUser());
    }
}
