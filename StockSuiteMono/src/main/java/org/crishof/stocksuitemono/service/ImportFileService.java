package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImportFileService {
    List<ProductRequest> readExcel(MultipartFile file);
}
