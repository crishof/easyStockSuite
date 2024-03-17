package org.crishof.imagesv.controller;

import org.crishof.imagesv.model.Image;
import org.crishof.imagesv.repository.ImageRepository;
import org.crishof.imagesv.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;
    @Autowired
    ImageRepository imageRepository;


    @PostMapping("/save")
    public UUID saveImage(@RequestBody byte[] fileBytes, @RequestParam String mime, @RequestParam String name) {

        return imageService.save(fileBytes, mime, name).getId();
    }

    @PutMapping("/update/{id}")
    public UUID updateImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {

        return imageService.update(id, file).getId();

    }


    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable UUID id) {

        imageService.deleteById(id);
    }
}