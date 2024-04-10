package com.crishof.productsv.service;

import com.crishof.productsv.dto.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImportFileService {
    List<ProductRequest> readExcel(MultipartFile file);
}
