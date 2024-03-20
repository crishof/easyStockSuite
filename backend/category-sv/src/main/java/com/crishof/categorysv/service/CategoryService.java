package com.crishof.categorysv.service;

import com.crishof.categorysv.dto.CategoryRequest;
import com.crishof.categorysv.dto.CategoryResponse;
import com.crishof.categorysv.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CategoryService {


    List<CategoryResponse> getAll();

    CategoryResponse getById(UUID id);

    Category getByName(String name);

    CategoryResponse save(CategoryRequest categoryRequest);

    CategoryResponse update(UUID id, String name);

    Category updateLogo(UUID id, UUID imageId);

    void deleteById(UUID id);

    Category updateImage(UUID uuid, String imageUrl);
}
