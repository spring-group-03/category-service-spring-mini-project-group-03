package com.example.categoriesservice.config;

import com.example.categoriesservice.exception.AuthHelper;
import com.example.categoriesservice.exception.NotFoundException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignConfig implements RequestInterceptor, ErrorDecoder {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + AuthHelper.getCurrentTokenUser());
    }

    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 404 -> new NotFoundException("Product not found");
            case 500 -> new RuntimeException("Product Server error");
            default -> new Exception("Generic error: " + response.status());
        };
    }

}
