package com.sarop.saropbackend.category.service;

import com.sarop.saropbackend.category.model.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(String name);

    Category updateCategory(String id, String name);

    List<Category> getAllCategories();

    void deleteCategory(String id);
}
