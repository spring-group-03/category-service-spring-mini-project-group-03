package com.example.categoriesservice.service;

import com.example.categoriesservice.emuns.CustomerProperty;
import com.example.categoriesservice.model.dto.request.CategoryRequest;
import com.example.categoriesservice.model.dto.respone.CategoryResponse;
import com.example.categoriesservice.model.dto.respone.Item;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory( CategoryRequest request);

    Item getAll(int page, int pageSize, CustomerProperty property, Sort.Direction direction);

    CategoryResponse getCategoryById(UUID id);

    CategoryResponse updateCategoryById(UUID id, CategoryRequest request);

    void deleteCategoryById(UUID id);
}
