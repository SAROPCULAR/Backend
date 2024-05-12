package com.sarop.saropbackend.category.controller;


import com.sarop.saropbackend.category.dto.CategorySaveRequest;
import com.sarop.saropbackend.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody CategorySaveRequest categorySaveRequest){
        return ResponseEntity.ok(categoryService.addCategory(categorySaveRequest.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable String id,@RequestBody String name){
        return ResponseEntity.ok(categoryService.updateCategory(id,name));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
