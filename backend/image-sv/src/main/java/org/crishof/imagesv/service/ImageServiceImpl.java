package org.crishof.imagesv.service;

import org.apache.commons.lang.StringUtils;
import org.crishof.imagesv.exception.FileParamsNullException;
import org.crishof.imagesv.exception.ImageIdNullException;
import org.crishof.imagesv.exception.ImageNotFoundException;
import org.crishof.imagesv.model.Image;
import org.crishof.imagesv.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Image save(byte[] fileBytes, String mime, String name) {

        if (fileBytes == null || fileBytes.length == 0 || StringUtils.isEmpty(mime) || StringUtils.isEmpty(name)) {
            throw new FileParamsNullException("File params cannot be null or empty");
        }

        Image image = new Image();
        image.setMime(mime);
        image.setName(name);
        image.setContent(fileBytes);
        return imageRepository.save(image);
    }

    @Override
    public Image update(UUID imageId, byte[] fileBytes, String mime, String name) {

        if (imageId == null) {
            throw new ImageIdNullException("Image ID cannot be null");
        }
        Optional<Image> optionalImage = imageRepository.findById(imageId);

        if (optionalImage.isEmpty()) {
            throw new ImageNotFoundException("Image with id: " + imageId + " not found");
        }
        if (fileBytes == null || fileBytes.length == 0 || StringUtils.isEmpty(mime) || StringUtils.isEmpty(name)) {
            throw new FileParamsNullException("File params cannot be null or empty");
        }

        Image image = optionalImage.get();
        image.setMime(mime);
        image.setName(name);
        image.setContent(fileBytes);
        return imageRepository.save(image);
    }

    @Override
    public void deleteById(UUID imageId) {
        if (imageId == null) {
            throw new ImageIdNullException("Image ID cannot be null");
        }
        imageRepository.deleteById(imageId);
    }

    @Override
    public Image getById(UUID imageId) {
        if (imageId == null) {
            throw new ImageIdNullException("Image ID cannot be null");
        }
        return imageRepository.getReferenceById(imageId);
    }
}

