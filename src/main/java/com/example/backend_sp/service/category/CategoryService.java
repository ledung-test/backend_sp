package com.example.backend_sp.service.category;

import com.example.backend_sp.request.CategoryRequest;
import com.example.backend_sp.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAllByOrderByCreatedAtDesc();

    List<CategoryResponse> findAllByActivatedTrue();

    void create(CategoryRequest request);

    void update(Integer id, CategoryRequest request);

}
