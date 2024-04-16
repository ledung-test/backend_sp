package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Category;
import com.example.backend_sp.request.CategoryRequest;
import com.example.backend_sp.response.CategoryResponse;

import java.util.List;

public class CategoryMapper {
    public static void mapCategoryRequestToCategory(CategoryRequest request, Category category){
        category.setName(request.getName());
        category.setActivated(request.isActivated());
    }

    public static void mapCategoryToCategoryResponse(Category category, CategoryResponse response){
        response.setId(category.getId());
        response.setName(category.getName());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());
        response.setActivated(category.isActivated());
    }

    public static void mapCategoryListToCategoryResponseList(List<Category> categories, List<CategoryResponse> categoryResponseList){
        if (!categories.isEmpty()){
            for (Category category : categories) {
                CategoryResponse categoryResponse = new CategoryResponse();
                CategoryMapper.mapCategoryToCategoryResponse(category, categoryResponse);
                categoryResponseList.add(categoryResponse);
            }
        }
    }
}
