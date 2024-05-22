package com.sarop.saropbackend.category.service.impl;

import com.sarop.saropbackend.category.dto.CategoryResponse;
import com.sarop.saropbackend.category.model.Category;
import com.sarop.saropbackend.category.repository.CategoryRepository;
import com.sarop.saropbackend.category.service.CategoryService;
import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final OperationRepository operationRepository;

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
    public List<CategoryResponse> getAllCategories(){
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for(Category category : categories){
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setName(category.getName());
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }


    @Override
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        for(Operation operation: category.getOperations()){
            operation.setCategory(null);
            operationRepository.save(operation);
        }
        categoryRepository.deleteById(id);
    }
}
