package com.example.backend_sp.service.category;

import com.example.backend_sp.entity.Category;
import com.example.backend_sp.mapper.CategoryMapper;
import com.example.backend_sp.repository.CategoryRepository;
import com.example.backend_sp.request.CategoryRequest;
import com.example.backend_sp.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public List<CategoryResponse> findAllByOrderByCreatedAtDesc() {
        List<Category> categories = repository.findAllByOrderByCreatedAtDesc();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        CategoryMapper.mapCategoryListToCategoryResponseList(categories, categoryResponseList);
        return categoryResponseList;
    }

    @Override
    public List<CategoryResponse> findAllByActivatedTrue() {
        List<Category> categories = repository.findAllByActivatedTrue();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        CategoryMapper.mapCategoryListToCategoryResponseList(categories, categoryResponseList);
        return categoryResponseList;
    }

    @Override
    public void create(CategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new RuntimeException("Danh mục đã tồn tại.");
        } else {
            Category category = new Category();
            CategoryMapper.mapCategoryRequestToCategory(request, category);
            repository.save(category);
        }
    }

    @Override
    public void update(Integer id, CategoryRequest request) {
        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục."));
        if (request.getName().equals(existingCategory.getName())){
            existingCategory.setName(existingCategory.getName());
        }else if (repository.existsByName(request.getName())){
            throw new RuntimeException("Danh mục đã tồn tại.");
        }else {
            existingCategory.setName(request.getName());
        }
        existingCategory.setActivated(request.isActivated());
        repository.save(existingCategory);
    }
}
