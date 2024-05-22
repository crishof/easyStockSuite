package com.crishof.categorysv.modelmapper;

import com.crishof.categorysv.dto.CategoryResponse;
import com.crishof.categorysv.model.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class CategoryMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static CategoryResponse toCategoryResponse(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }
}
