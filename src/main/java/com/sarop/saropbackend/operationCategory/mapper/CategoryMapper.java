package com.sarop.saropbackend.operationCategory.mapper;

import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operationCategory.dto.CategorySaveRequest;
import com.sarop.saropbackend.operationCategory.dto.CategoryUpdateRequest;
import com.sarop.saropbackend.operationCategory.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CategoryMapper {

    public Category categorySaveRequestToCategory(CategorySaveRequest categorySaveRequest){
        var category = Category.builder().id(Util.generateUUID()).categoryName(categorySaveRequest.getCategoryName())
                .operationList(new ArrayList<Operation>()).build();
        return category;
    }
    public Category categoryUpdateRequestToCategory(Category category, CategoryUpdateRequest categoryUpdateRequest){
        category.setCategoryName(categoryUpdateRequest.getCategoryName());
        category.setOperationList(categoryUpdateRequest.getOperationList());
        return category;
    }
}
