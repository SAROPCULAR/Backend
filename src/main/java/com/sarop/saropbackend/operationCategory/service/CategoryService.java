package com.sarop.saropbackend.operationCategory.service;

import com.sarop.saropbackend.operationCategory.dto.CategorySaveRequest;
import com.sarop.saropbackend.operationCategory.dto.CategoryUpdateRequest;
import com.sarop.saropbackend.operationCategory.model.Category;

import java.util.List;


public interface CategoryService {
    Category saveCategory(CategorySaveRequest categorySaveRequest);
    Category updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest);
    List<Category> getCategories();

    void deleteCategory(String id);
}
