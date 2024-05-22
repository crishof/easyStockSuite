package org.crishof.imagesv.controller;

import lombok.RequiredArgsConstructor;
import org.crishof.imagesv.service.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final CloudinaryService cloudinaryService;


    @PostMapping("/save")
    public ResponseEntity<String> saveImage(@RequestBody byte[] fileBytes, @RequestParam String mime,
                                            @RequestParam String name, @RequestParam String entityName) {

        try {
            if (fileBytes == null || mime.isEmpty() || name.isEmpty() || entityName.isEmpty()) {
                return ResponseEntity.badRequest().body("Missing or invalid input parameters");
            }

            MultipartFile file = new MockMultipartFile(name, name, mime, fileBytes);
            String imageUrl = cloudinaryService.uploadImage(file, entityName);
            return ResponseEntity.ok().body(imageUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input parameters");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateImage(@RequestBody byte[] fileBytes, @RequestParam String mime,
                                              @RequestParam String name, @RequestParam String entityName,
                                              @RequestParam String preUrl) {
        try {
            if (fileBytes == null || mime.isEmpty() || name.isEmpty() || entityName.isEmpty() || preUrl.isEmpty()) {
                return ResponseEntity.badRequest().body("Missing or invalid input parameters");
            }

            deleteImageByUrl(preUrl, entityName);

            MultipartFile file = new MockMultipartFile(name, name, mime, fileBytes);
            String imageUrl = cloudinaryService.uploadImage(file, entityName);
            return ResponseEntity.ok().body(imageUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input parameters");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update image");
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImageByUrl(@RequestParam String url, @RequestParam String entityName) {
        try {
            cloudinaryService.deleteImageByUrl(url, entityName);
            return ResponseEntity.ok().body("Image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image");
        }
    }
}