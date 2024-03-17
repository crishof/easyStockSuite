package com.crishof.categorysv.apiCient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "image-sv")
public interface ImageAPIClient {
    @PutMapping("/update/{id}")
    UUID updateImage(@PathVariable UUID id, @RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name);

    @PostMapping("/image/save")
    UUID saveImage(@RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name);

    @DeleteMapping("/image/delete/{id}")
    void deleteImage(@PathVariable UUID id);
}