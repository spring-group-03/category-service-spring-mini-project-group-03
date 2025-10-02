package com.example.categoriesservice.controller;

import com.example.categoriesservice.emuns.CustomerProperty;
import com.example.categoriesservice.model.dto.request.CategoryRequest;
import com.example.categoriesservice.model.dto.respone.*;
import com.example.categoriesservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController extends BaseResponse {

    private final CategoryService service;

    @PostMapping
    @Operation(summary = "Create category",
            description = " Creates a new category record in the system using the provided categoryRequest. Returns the details of the newly created category.")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory (
            @Valid @RequestBody CategoryRequest request){
        return responseEntity(
                true,
                "Category create successfully",
                HttpStatus.CREATED,
                service.createCategory(request));
    }

    @GetMapping
    @Operation(summary = "Get pagination list categories",
            description = "Retrieves a list of category in paginated format. You can specify the page number, page size, sorting property (from CustomizeProperty enum), and sort direction")
    public ResponseEntity<ApiResponse<Item>> getAll ( @RequestParam (defaultValue = "1") int page,
                                                      @RequestParam (defaultValue = "10")int size,
                                                      @RequestParam CustomerProperty sortBy,
                                                      @RequestParam Sort.Direction direction){
        return responseEntity(
                true,
                "Get all categories successfully",
                HttpStatus.OK,
                service.getAll(page,size,sortBy,direction));
    }


    @GetMapping("/{category_id}")
    @Operation(summary = "Get category by id",description = "Retrieves the details of a category using the specified product ID." )
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById (@PathVariable("category_id") UUID id){

        return responseEntity(
                true,
                "Get category by id successfully",
                HttpStatus.OK,
                service.getCategoryById(id));
    }

    @PutMapping("/{category_id}")
    @Operation(summary = "Update category by id",
            description = "Updates the details of an existing category using the provided category ID and categoryRequest payload.")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategoryById (@PathVariable("category_id") UUID id,
                                                                             @RequestBody CategoryRequest request) {
        return responseEntity(
                true,
                "Update category successfully.",
                HttpStatus.OK,
                service.updateCategoryById(id, request)
        );
    }

        @DeleteMapping("/{category_id}")
        @Operation(summary = "Delete category by id",
                description = "Deletes an existing category from the system using the specified category ID.")
        public ResponseEntity<ApiResponse<Void>> deleteCategoryById (@PathVariable("category_id") UUID id){
            service.deleteCategoryById(id);
            return responseEntity(
                    true,
                    "Delete category successfully.",
                    HttpStatus.OK
            );
    }


}
