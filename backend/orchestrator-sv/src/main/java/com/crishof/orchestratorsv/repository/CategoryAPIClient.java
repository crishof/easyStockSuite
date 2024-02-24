package com.crishof.orchestratorsv.repository;

import com.crishof.orchestratorsv.dto.CategoryDTO;
import com.crishof.orchestratorsv.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "category-sv")
public interface CategoryAPIClient {
    @GetMapping("/category/getById/{categoryId}")
    CategoryDTO getCategoryInfo(@PathVariable("categoryId") Long id);

    @PostMapping("/category/save")
    String save(Category category);

    @GetMapping("/category/findByName/{category}")
    Long getCategoryIdByName(@PathVariable("category") String category);

    @PostMapping("/category/saveCategoryName")
    void saveCategoryName(@RequestBody String category);
}