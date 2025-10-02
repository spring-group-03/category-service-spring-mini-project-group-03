package com.example.categoriesservice.service.serviceImpl;

import com.example.categoriesservice.client.AuthClient;
import com.example.categoriesservice.client.ProductClient;
import com.example.categoriesservice.emuns.CustomerProperty;
import com.example.categoriesservice.exception.ConflictException;
import com.example.categoriesservice.exception.NotFoundException;
import com.example.categoriesservice.model.dto.request.CategoryRequest;
import com.example.categoriesservice.model.dto.respone.CategoryResponse;
import com.example.categoriesservice.model.dto.respone.Item;
import com.example.categoriesservice.model.dto.respone.Pagination;
import com.example.categoriesservice.model.dto.respone.UserResponse;
import com.example.categoriesservice.model.entity.Category;
import com.example.categoriesservice.repository.CategoryRepository;
import com.example.categoriesservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final AuthClient client;
    private final ProductClient productClient;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        UserResponse response= client.getCurrentUserProfile().getBody().getPayload();
       Category category= repository.findByName(request.getName());
       if (category != null){
           throw new ConflictException("The category with this name: "+request.getName() +"already used.");
       }

        return repository.save(request.toCategory(response.getUserId())).toResponse(response);
    }

    @Override
    public Item getAll(int page, int size,  CustomerProperty customerProperty, Sort.Direction direction) {

        String sortField;
        switch (customerProperty) {
            case CATEGORY_ID  -> sortField = "id";
            case CATEGORY_NAME -> sortField= "name";
            default -> throw new IllegalArgumentException("Invalid order property: " + customerProperty);
        }
        UserResponse  userResponse= client.getCurrentUserProfile().getBody().getPayload();
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(direction, sortField));
        Page<Category> customerPage= repository.findAllByUserId(userResponse.getUserId(),pageable);


        List<CategoryResponse> responses= customerPage.getContent().stream().map(category -> category.toResponse(userResponse)).toList();
        return Item.builder()
                .items(responses)
                .pagination(Pagination.builder()
                        .totalElements(customerPage.getTotalElements())
                        .pageSize(customerPage.getSize())
                        .currentPage(customerPage.getNumber()+1)
                        .totalPages(customerPage.getTotalPages())
                        .build())
                .build();
    }

    @Override
    public CategoryResponse getCategoryById(UUID id) {
        UserResponse userResponse= client.getCurrentUserProfile().getBody().getPayload();
        Category category = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
        if (!category.getUserId().equals(userResponse.getUserId())){
            throw new ConflictException("This category not belong to you");
        }

        return category.toResponse(userResponse);
    }

    @Override
    public CategoryResponse updateCategoryById(UUID id, CategoryRequest request) {
        UserResponse userResponse= client.getCurrentUserProfile().getBody().getPayload();
        getCategoryById(id);
        Category category1= repository.findByName(request.getName());
        if (category1 != null){
            throw new ConflictException("The category with this name: "+request.getName() +"already used.");
        }

        Category category= Category.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .userId(userResponse.getUserId())
                .build();
        return repository.save(category).toResponse(userResponse) ;
    }

    @Override
    public void deleteCategoryById(UUID id) {
        getCategoryById(id);
        productClient.deleteProductByCategoryId(id);
        repository.deleteById(id);
    }

}
