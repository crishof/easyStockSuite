package com.crishof.categorysv.apiCient;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@FeignClient(name = "image-sv")
public interface ImageAPIClient {


    @PostMapping("image/test")
    public String test(@RequestBody byte[] fileBytes,@RequestParam String mime,@RequestParam String name);
    @PutMapping("/image/update/{id}")
    UUID updateImage(@PathVariable UUID id, @RequestPart("file") MultipartFile file);

    @PostMapping("/image/save")
    UUID saveImage(@RequestBody byte[] fileBytes,@RequestParam String mime,@RequestParam String name);

    @DeleteMapping("/image/delete/{id}")
    void deleteImage(@PathVariable UUID id);
}
