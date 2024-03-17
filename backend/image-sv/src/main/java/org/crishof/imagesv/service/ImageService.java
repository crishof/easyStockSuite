package org.crishof.imagesv.service;

import org.crishof.imagesv.model.Image;

import java.util.UUID;

public interface ImageService {

    Image save(byte[] fileBytes, String mime, String name);

    Image update(UUID id, byte[] fileBytes, String mime, String name);

    void deleteById(UUID uuid);

    Image getById(UUID imageId);
}