package org.crishof.imagesv.controller;

import org.crishof.imagesv.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/save")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, String entityName) {
        try {
            String imageUrl = cloudinaryService.uploadImage(file, entityName);
            return ResponseEntity.ok().body(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PostMapping("/saveBytes")
    public ResponseEntity<String> saveImage(@RequestBody byte[] fileBytes, @RequestParam String mime,
                                            @RequestParam String name, @RequestParam String entityName) {

        MultipartFile file = new MockMultipartFile(name, name, mime, fileBytes);
        try {
            String imageUrl = cloudinaryService.uploadImage(file, entityName);
            System.out.println(ResponseEntity.ok().body(imageUrl));
            return ResponseEntity.ok().body(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PutMapping("/update")
    public String updateImage(@RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name,
                              @RequestParam String entityName, @RequestParam String preUrl) {

        this.deleteImageByUrl(preUrl, entityName);
        return this.saveImage(fileBytes, mime, name, entityName).getBody();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImageByUrl(@RequestParam String url, @RequestParam String entityName) {

        System.out.println("ENTRANDO AL CONTROLLER");

        System.out.println("url = " + url);
        System.out.println("entityName = " + entityName);
        try {
            cloudinaryService.deleteImageByUrl(url, entityName);
            return ResponseEntity.ok().body("Image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image");
        }
    }
}