package com.sarop.saropbackend.category.service.impl;

import com.sarop.saropbackend.category.model.Category;
import com.sarop.saropbackend.category.repository.CategoryRepository;
import com.sarop.saropbackend.category.service.CategoryService;
import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.operation.model.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(String name) {
        var category = Category.builder().id(Util.generateUUID()).name(name).operations(new ArrayList<Operation>()).build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(String id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
