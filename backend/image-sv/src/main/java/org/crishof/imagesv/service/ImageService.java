package org.crishof.imagesv.service;

import org.crishof.imagesv.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {

    Image save(byte[] fileBytes, String mime, String name);

    Image update(UUID idImage, MultipartFile file);

    void deleteById(UUID uuid);
}