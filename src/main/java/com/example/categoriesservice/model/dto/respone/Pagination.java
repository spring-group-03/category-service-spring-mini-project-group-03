package com.example.categoriesservice.model.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}
