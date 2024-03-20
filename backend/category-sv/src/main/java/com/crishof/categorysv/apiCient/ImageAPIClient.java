package com.crishof.categorysv.apiCient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-sv")
public interface ImageAPIClient {

    @PostMapping("/save")
    ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, String folderName);

    @PostMapping("/saveBytes")
    ResponseEntity<String> saveImage(@RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name, @RequestParam String folderName);

    @PutMapping("/update")
    String updateImage(@RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name, @RequestParam String folderName, @RequestParam String preUrl);

    @DeleteMapping("/delete")
    ResponseEntity<String> deleteImageByUrl(@RequestParam String url, @RequestParam String entityName);
}