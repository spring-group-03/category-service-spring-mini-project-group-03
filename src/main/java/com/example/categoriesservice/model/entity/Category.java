package com.example.categoriesservice.model.entity;

import com.example.categoriesservice.model.dto.respone.CategoryResponse;
import com.example.categoriesservice.model.dto.respone.UserResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "user_id",nullable = false)
    private String userId;

    public CategoryResponse toResponse(UserResponse response ){
      return  CategoryResponse.builder()
              .categoryId(this.id)
              .name(this.name.trim())
              .description(this.description.trim())
              .userResponse(response)
              .build();
    };
}
