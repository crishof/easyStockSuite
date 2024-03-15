package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {

    Image save(MultipartFile file);

    Image update(UUID idImage, MultipartFile file);

    void deleteById(UUID uuid);
}
