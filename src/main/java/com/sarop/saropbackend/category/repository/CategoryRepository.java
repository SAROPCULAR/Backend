package com.sarop.saropbackend.category.repository;

import com.sarop.saropbackend.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
    Category findCategoryByName(String name);
}
