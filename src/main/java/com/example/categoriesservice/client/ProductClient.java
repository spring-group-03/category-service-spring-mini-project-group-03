package com.example.categoriesservice.client;

import com.example.categoriesservice.config.OpenFeignConfig;
import com.example.categoriesservice.model.dto.respone.ApiResponse;
import com.example.categoriesservice.model.dto.respone.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ClientProduct",
            url = "http://localhost:8085/api/v1/product",
            configuration = OpenFeignConfig.class
)
public interface ProductClient {
    @DeleteMapping("/category/{categoryId}")
     ResponseEntity<ApiResponse<ProductResponse>> deleteProductByCategoryId(
            @PathVariable("categoryId") UUID categoryId);
}
