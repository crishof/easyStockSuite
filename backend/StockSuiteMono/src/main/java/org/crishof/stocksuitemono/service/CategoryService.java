package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAll();

    Category getById(UUID id);

    Category getByName(String name);

    CategoryResponse save(CategoryRequest categoryRequest);

    Category update(UUID id, String name);

    Category updateLogo(UUID id, MultipartFile logo);

    void deleteById(UUID id);
}
