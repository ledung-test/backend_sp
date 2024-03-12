package com.example.backend_sp.service;

import com.example.backend_sp.entity.Category;
import com.example.backend_sp.request.CategoryRequest;

import java.util.List;

public interface CategoryService {

    List<Category> findAllByActivatedTrue();

    Category findById(Integer id);

    Category save(CategoryRequest request);

    Category update(Integer id, CategoryRequest request);

    void deleteById(Integer id);
}
