package com.sarop.saropbackend.category.service;

import com.sarop.saropbackend.category.model.Category;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category addCategory(String name);

    Category updateCategory(String id, String name);

    List<Category> getAllCategories();

    void deleteCategory(String id);
}
