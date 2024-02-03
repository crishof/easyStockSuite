package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.ProductRequest;

import java.util.List;

public interface ImportFileService {
    public List<ProductRequest> readExcel(String filePath);
}
