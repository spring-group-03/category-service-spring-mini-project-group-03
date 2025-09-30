package com.example.categoriesservice.model.dto.request;

import com.example.categoriesservice.model.entity.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank
    @Size(min = 2,
            max =  255,
            message = "name must be bigger tha 2 character and max only 255 character")
    private String name;
    private String description;



    public Category toCategory(String id){
        return  Category.builder()
                .id(null)
                .name(this.name)
                .description(this.description)
                .userId(id)
                .build();
    }
}

