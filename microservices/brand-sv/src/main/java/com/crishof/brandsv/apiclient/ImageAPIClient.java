package com.crishof.brandsv.apiclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "image-sv", url = "http://localhost:9005")
public interface ImageAPIClient {

    @PostMapping("/image/save")
    ResponseEntity<String> saveImage(@RequestBody byte[] fileBytes, @RequestParam String mime,
                                     @RequestParam String name, @RequestParam String entityName);

    @PutMapping("/image/update")
    String updateImage(@RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name,
                       @RequestParam String entityName, @RequestParam String preUrl);

    @DeleteMapping("/image/delete")
    ResponseEntity<String> deleteImageByUrl(@RequestParam String url, @RequestParam String entityName);
}