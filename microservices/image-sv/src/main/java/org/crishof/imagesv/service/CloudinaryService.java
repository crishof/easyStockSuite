package org.crishof.imagesv.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.crishof.imagesv.exception.FileDeleteException;
import org.crishof.imagesv.exception.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file, String entityName) {
        try {
            Map<String, Object> options = ObjectUtils.asMap("folder", entityName);
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload image to Cloudinary", e);
        }
    }

    public void deleteImageByUrl(String imageUrl, String entityName) {
        try {
            String publicId = extractPublicIdFromUrl(imageUrl, entityName);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new FileDeleteException("Failed to delete image from Cloudinary", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image from Cloudinary", e);
        }
    }

    public String extractPublicIdFromUrl(String url, String entityName) {
        String folder = entityName.concat("/");
        int folderIndex = url.indexOf(folder) + folder.length();
        int nextDotIndex = url.indexOf(".", folderIndex);
        return folder + url.substring(folderIndex, nextDotIndex);
    }
}

