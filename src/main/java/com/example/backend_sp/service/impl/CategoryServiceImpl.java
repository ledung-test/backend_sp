package com.example.backend_sp.service.impl;

import com.example.backend_sp.entity.Category;
import com.example.backend_sp.repository.CategoryRepository;
import com.example.backend_sp.request.CategoryRequest;
import com.example.backend_sp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public List<Category> findAllByActivatedTrue() {
        List<Category> categories = repository.findAllByActivatedTrue();
        if (categories.isEmpty()) {
            throw new RuntimeException("Danh sách danh mục trống.");
        }
        return categories;
    }

    @Override
    public Category findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục."));
    }

    @Override
    public Category save(CategoryRequest request) {
        if (repository.existsByNameAndActivatedTrue(request.getName())) {
            throw new RuntimeException("Danh mục đã tồn tại.");
        } else {
            Category category = Category.builder()
                    .name(request.getName())
                    .activated(true)
                    .deleted(false)
                    .build();
            return repository.save(category);
        }
    }

    @Override
    public Category update(Integer id, CategoryRequest request) {
        Category existingCategory = findById(id);
        if (repository.existsByNameAndActivatedTrue(request.getName())){
            throw new RuntimeException("Danh mục đã tồn tại.");
        }else {
            existingCategory.setName(request.getName());
            return repository.save(existingCategory);
        }
    }

    @Override
    public void deleteById(Integer id) {
        Category existingCategory = findById(id);
        existingCategory.setActivated(false);
        existingCategory.setDeleted(true);
        repository.save(existingCategory);
    }
}
