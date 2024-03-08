package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Image;
import org.crishof.stocksuitemono.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Image save(MultipartFile file) {

        if (file != null) {
            try {
                Image image = new Image();
                image.setMime(file.getContentType());
                image.setName(file.getOriginalFilename());
                image.setContent(file.getBytes());
                return imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public Image update(UUID idImage, MultipartFile file) {

        if (file != null) {
            try {
                Image image = new Image();

                if (idImage != null) {
                    Optional<Image> response = imageRepository.findById(idImage);
                    if (response.isPresent()) {
                        image = response.get();
                    }
                }
                image.setMime(file.getContentType());
                image.setName(file.getOriginalFilename());
                image.setContent(file.getBytes());
                return imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void deleteById(UUID uuid) {
        imageRepository.deleteById(uuid);
    }
}
