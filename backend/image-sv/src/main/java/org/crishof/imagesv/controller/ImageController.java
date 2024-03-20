package org.crishof.imagesv.controller;

import org.crishof.imagesv.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/save")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, String folderName) {
        try {
            String imageUrl = cloudinaryService.uploadImage(file, folderName);
            return ResponseEntity.ok().body(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PostMapping("/saveBytes")
    public ResponseEntity<String> saveImage(@RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name, @RequestParam String folderName) {

        MultipartFile file = new MockMultipartFile(name, name, mime, fileBytes);


        try {
            String imageUrl = cloudinaryService.uploadImage(file, folderName);
            return ResponseEntity.ok().body(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PutMapping("/update/{id}")
    public String updateImage(@PathVariable UUID id, @RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name) {
        return "";
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable UUID id) {
    }
}