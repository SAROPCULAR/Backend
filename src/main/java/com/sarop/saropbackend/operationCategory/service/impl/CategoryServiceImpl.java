package com.sarop.saropbackend.operationCategory.service.impl;

import com.sarop.saropbackend.operationCategory.dto.CategorySaveRequest;
import com.sarop.saropbackend.operationCategory.dto.CategoryUpdateRequest;
import com.sarop.saropbackend.operationCategory.mapper.CategoryMapper;
import com.sarop.saropbackend.operationCategory.model.Category;
import com.sarop.saropbackend.operationCategory.repository.CategoryRepository;
import com.sarop.saropbackend.operationCategory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public Category saveCategory(CategorySaveRequest categorySaveRequest) {
        var category = categoryMapper.categorySaveRequestToCategory(categorySaveRequest);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest) {
        var category = categoryRepository.findById(id).orElseThrow();
        category = categoryMapper.categoryUpdateRequestToCategory(category,categoryUpdateRequest);
        return category;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
