package com.example.backend_sp.service.category;

import com.example.backend_sp.entity.Category;
import com.example.backend_sp.request.CategoryRequest;
import com.example.backend_sp.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAllByActivatedTrue();

    Category findById(Integer id);

    Category save(CategoryRequest request);

    Category update(Integer id, CategoryRequest request);

    void deleteById(Integer id);
}
