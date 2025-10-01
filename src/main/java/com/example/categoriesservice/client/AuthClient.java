package com.example.categoriesservice.client;

import com.example.categoriesservice.config.OpenFeignConfig;
import com.example.categoriesservice.model.dto.respone.ApiResponse;
import com.example.categoriesservice.model.dto.respone.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "auth-service",
        path = "api/v1/profiles",
        configuration = OpenFeignConfig.class)
public interface AuthClient {

    @GetMapping
    ResponseEntity<ApiResponse<UserResponse>> getCurrentUserProfile();
}
