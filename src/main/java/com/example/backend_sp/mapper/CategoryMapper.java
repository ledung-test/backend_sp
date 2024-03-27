package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Category;
import com.example.backend_sp.request.CategoryRequest;
import com.example.backend_sp.response.CategoryResponse;

public class CategoryMapper {
    public static void mapCategoryRequestToCategory(CategoryRequest request, Category category){
        category.setName(request.getName());
        category.setActivated(true);
        category.setDeleted(false);
    }

    public static void mapCategoryToCategoryResponse(Category category, CategoryResponse response){
        response.setId(category.getId());
        response.setName(category.getName());
        response.setActivated(category.isActivated());
    }
}
