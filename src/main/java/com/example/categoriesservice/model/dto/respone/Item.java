package com.example.categoriesservice.model.dto.respone;

import com.example.categoriesservice.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Item {
    private List<CategoryResponse>  items;
    private Pagination pagination;
}
