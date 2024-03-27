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
    public List<CategoryResponse> findAllByActivatedTrue() {
        List<Category> categories = repository.findAllByActivatedTrue();
        if (categories.isEmpty()) {
            throw new RuntimeException("Danh sách danh mục trống.");
        }
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponse categoryResponse = CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .activated(category.isActivated())
                    .build();
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
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
            Category category = new Category();
            CategoryMapper.mapCategoryRequestToCategory(request, category);
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
