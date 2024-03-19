package com.sarop.saropbackend.operationCategory.controller;

import com.sarop.saropbackend.operationCategory.dto.CategorySaveRequest;
import com.sarop.saropbackend.operationCategory.dto.CategoryUpdateRequest;
import com.sarop.saropbackend.operationCategory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategorySaveRequest categorySaveRequest){
        return ResponseEntity.ok(categoryService.saveCategory(categorySaveRequest));
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<?> updateCategory(String id,@RequestBody CategoryUpdateRequest categoryUpdateRequest){
        return ResponseEntity.ok(categoryService.updateCategory(id,categoryUpdateRequest));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(String id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
