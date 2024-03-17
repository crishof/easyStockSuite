package com.crishof.categorysv.modelMapper;

import com.crishof.categorysv.dto.CategoryResponse;
import com.crishof.categorysv.model.Category;
import org.modelmapper.ModelMapper;

public class CategoryMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static CategoryResponse toCategoryResponse(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }
}
