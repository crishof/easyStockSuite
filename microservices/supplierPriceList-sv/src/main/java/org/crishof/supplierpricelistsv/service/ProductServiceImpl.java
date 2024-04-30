package org.crishof.supplierpricelistsv.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.crishof.supplierpricelistsv.dto.ProductResponse;
import org.crishof.supplierpricelistsv.exception.ImportFileException;
import org.crishof.supplierpricelistsv.exception.ProductNotFoundException;
import org.crishof.supplierpricelistsv.model.Product;
import org.crishof.supplierpricelistsv.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;

    Logger logger = Logger.getLogger(ProductServiceImpl.class.getName());
    int maxLength = 255;

    @Override
    public Product findProductByBrandAndCodeAndSupplierId(String brand, String code, UUID supplierId) {
        return productRepository.findProductByBrandAndCodeAndSupplierId(brand, code, supplierId);
    }

    @Override
    public List<ProductResponse> getAllByFilter(UUID supplierId, String brand, String filter) {
        List<Product> products = new ArrayList<>();

        if (supplierId == null) {
            if (brand == null && filter == null) {
                logger.info("// Supplier null && brand null && filter null");
                products = productRepository.findAll();
            } else if (brand == null) {
                logger.info("// Supplier null && brand null && filter");
                products = productRepository.findAllByBrandContainingOrModelContainingOrDescriptionContaining(filter);
            } else if (filter == null) {
                logger.info("// Supplier null && brand && filter null");
                products = productRepository.findAllByBrand(brand);
            } else {
                logger.info("// Supplier null && brand && filter");
                products = productRepository.findAllByBrandAndModelContainingOrDescriptionContaining(brand, filter);
            }
        }

        if (supplierId != null) {
            if (brand == null && filter == null) {
                logger.info("// Supplier && brand null && filter null");
                products = productRepository.findAllBySupplierId(supplierId);
            } else if (brand == null) {
                logger.info("// Supplier && brand null && filter");
                products = productRepository.findAllBySupplierIdAndBrandContainingOrModelContainingOrDescriptionContaining(supplierId, filter);
            } else if (filter == null) {
                logger.info("// Supplier && brand && filter null");
                products = productRepository.findAllBySupplierIdAndBrand(supplierId, brand);
            } else {
                logger.info("// Supplier && brand && filter");
                products = productRepository.findAllBySupplierIdAndBrandAndCodeContainingOrDescriptionContaining(supplierId, brand, filter);
            }
        }
        return products.stream().map(this::toProductResponse).toList();
    }

    @Override
    public ProductResponse getById(UUID id) throws ProductNotFoundException {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return this.toProductResponse(product);
    }

    public ProductResponse toProductResponse(Product product) {

        new ProductResponse();
        return ProductResponse.builder()
                .id(product.getId())
                .supplierId(product.getSupplierId())
                .brand(product.getBrand())
                .code(product.getCode())
                .model(product.getModel())
                .description(product.getDescription())
                .category(product.getCategory())
                .lastUpdate(product.getLastUpdate())
                .price(product.getPrice())
                .suggestedPrice(product.getSuggestedPrice())
                .suggestedWebPrice(product.getSuggestedWebPrice())
                .stockAvailable(product.getStockAvailable())
                .barcode(product.getBarcode())
                .currency(product.getCurrency())
                .taxRate(product.getTaxRate())
                .build();

    }

    public List<Product> readExcel(MultipartFile file) throws IOException {

        List<Product> products = new ArrayList<>();

        Workbook workbook;
        try (InputStream inputStream = file.getInputStream()) {
            workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet.getPhysicalNumberOfRows() < 2) {
                throw new ImportFileException("The file does not have enough rows to process.");
            }

            List<String> titles = extractTitles(sheet.getRow(0));
            processRows(sheet, titles, products);

        } catch (IOException | EncryptedDocumentException e) {
            throw new ImportFileException("Error while reading excel file. Import cancelled");
        }

        workbook.close();
        return products;
    }

    private List<String> extractTitles(Row titleRow) {
        List<String> titles = new ArrayList<>();
        for (Cell titleCell : titleRow) {
            titles.add(titleCell.getStringCellValue().toLowerCase());
        }
        return titles;
    }

    private void processRows(Sheet sheet, List<String> titles, List<Product> products) {

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Product product = parseProductRow(row, titles);
                products.add(product);
            }
        }
    }

    private Product parseProductRow(Row row, List<String> titles) {
        Product product = new Product();

        Iterator<Cell> cellIterator = row.cellIterator();
        int columnIndex = 0;

        DataFormatter dataFormatter = new DataFormatter();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            String cellContent = dataFormatter.formatCellValue(cell);

            if (columnIndex < titles.size()) {
                applyCellToProduct(product, titles.get(columnIndex).toLowerCase(), cellContent);
            }
            columnIndex++;
        }
        product.setLastUpdate(LocalDate.now());
        return product;
    }

    private void applyCellToProduct(Product product, String title, String cellContent) {

        switch (title) {
            case "brand":
                product.setBrand(cellContent);
                break;
            case "code":
                product.setCode(determineValue(cellContent, null));
                break;
            case "model":
                product.setModel(determineValue(cellContent, null));
                break;
            case "description":
                if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                    if (cellContent.length() > maxLength) {
                        cellContent = cellContent.substring(0, maxLength);
                    }
                    product.setDescription(cellContent);

                } else product.setDescription(null);
                break;
            case "category":
                product.setCategory(determineValue(cellContent, null));
                break;
            case "price":
                product.setPrice(parsePrice(cellContent));
                break;
            case "tax-rate":
                product.setTaxRate(parseTaxRate(cellContent));
                break;
            case "suggested-price":
                product.setSuggestedPrice(parsePrice(cellContent));
                break;
            case "suggested-web-price":
                product.setSuggestedWebPrice(parsePrice(cellContent));
                break;
            case "stock":
                product.setStockAvailable(determineValue(cellContent, null));
                break;
            case "bar-code":
                product.setBarcode(determineValue(cellContent, null));
                break;
            case "currency":
                product.setCurrency(determineValue(cellContent, "$"));
                break;
            default:
                break;
        }
    }

    private double parseTaxRate(String cellContent) {

        double defaultTaxRate = 0.21;

        if (!cellContent.isBlank() || !cellContent.isEmpty()) {
            return defaultTaxRate;
        }

        cellContent = cellContent.trim().replace(",", "").replace(" ", "");

        if (cellContent.endsWith("%")) {
            cellContent = cellContent.substring(0, cellContent.length() - 1);

            try {
                return Double.parseDouble(cellContent) / 100.0;
            } catch (NumberFormatException e) {
                return defaultTaxRate;
            }
        } else {
            try {
                return Double.parseDouble(cellContent);
            } catch (NumberFormatException e) {
                return defaultTaxRate;
            }
        }
    }

    private double parsePrice(String cellContent) {
        if (cellContent.isBlank() || cellContent.isEmpty()) {
            return parseDouble(cellContent.replace(",", "").replace(" ", ""));
        }
        return 0;
    }

    private String determineValue(String cellContent, String defaultValue) {
        return cellContent.isBlank() ? defaultValue : cellContent;
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }
}
