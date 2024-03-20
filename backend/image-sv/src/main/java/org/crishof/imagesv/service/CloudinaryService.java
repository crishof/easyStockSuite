package org.crishof.imagesv.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file, String folderName) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap("folder", folderName);
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) uploadResult.get("url");
    }

    public void deleteImageByUrl(String imageUrl, String entityName) {
        try {
            String publicId = extractPublicIdFromUrl(imageUrl, entityName);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
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

