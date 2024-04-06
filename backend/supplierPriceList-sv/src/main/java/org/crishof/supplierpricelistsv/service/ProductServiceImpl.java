package org.crishof.supplierpricelistsv.service;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.crishof.supplierpricelistsv.dto.ProductResponse;
import org.crishof.supplierpricelistsv.exception.ImportFileException;
import org.crishof.supplierpricelistsv.model.Product;
import org.crishof.supplierpricelistsv.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> readExcel(MultipartFile file) {
        List<Product> products = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del archivo

            // Verifica si hay al menos dos filas en la hoja
            if (sheet.getPhysicalNumberOfRows() < 2) {
                // No hay suficientes filas para procesar
                throw new RuntimeException("El archivo no tiene suficientes filas para procesar.");
            }

            // Obtén la fila de títulos (fila 1)
            Row titleRow = sheet.getRow(0);
            List<String> titles = new ArrayList<>();

            // Itera sobre las celdas de la fila de títulos y almacena los títulos en minúsculas
            for (Cell titleCell : titleRow) {
                titles.add(titleCell.getStringCellValue().toLowerCase());
            }

            // Itera sobre cada fila en la hoja, comenzando desde la segunda fila (índice 1)
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                // Verifica si la fila no es nula antes de procesarla
                if (row != null) {
                    Iterator<Cell> cellIterator = row.cellIterator();

                    // Crea un nuevo objeto Product por cada fila
                    Product product = new Product();

                    // Itera sobre cada celda en la fila
                    int columnIndex = 0;

                    DataFormatter dataFormatter = new DataFormatter();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        // Usa DataFormatter para obtener una representación formateada del valor de la celda
                        String cellContent = dataFormatter.formatCellValue(cell);

                        // Verifica si el título correspondiente a esta celda está en la lista de títulos
                        if (columnIndex < titles.size()) {
                            String title = titles.get(columnIndex);

                            int maxLength = 255;

                            // Asigna el valor de la celda al atributo correspondiente según el título
                            switch (title) {
                                case "brand":
                                    product.setBrand(cellContent);
                                    break;
                                case "code":
                                    product.setCode(cellContent);
                                    break;
                                case "model":
                                    product.setModel(cellContent);
                                    break;
                                case "description":
                                    if (cellContent.length() > maxLength) {
                                        cellContent = cellContent.substring(0, maxLength);
                                    }
                                    product.setDescription(cellContent);
                                    break;
                                case "category":
                                    product.setCategory(cellContent);
                                    break;
                                case "price", "tax-rate", "suggested-price", "suggested-web-price":

                                    // Elimina caracteres no deseados, como comas
                                    cellContent = cellContent.replaceAll(",", "").replaceAll(" ", "");

                                    // Asigna el valor convertido a double
                                    double numericValue = Double.parseDouble(cellContent);

                                    // Asigna el valor al atributo correspondiente

                                    switch (title) {
                                        case "price":
                                            product.setPrice(numericValue);
                                            break;
                                        case "tax-rate":
                                            product.setTaxRate(numericValue);
                                            break;
                                        case "suggested-price":
                                            product.setSuggestedPrice(numericValue);
                                            break;
                                        case "suggested-web-price":
                                            product.setSuggestedWebPrice(numericValue);
                                            break;
                                    }
                                case "stock":
                                    product.setStockAvailable(cellContent);
                                    break;
                                case "bar-code":
                                    product.setBarcode(cellContent);
                                    break;
                                case "currency":
                                    product.setCurrency(cellContent);
                                    break;
                            }
                            product.setLastUpdate(LocalDate.now());
                        }

                        columnIndex++;
                    }

                    products.add(product);
                }
            }

            workbook.close();

        } catch (IOException | EncryptedDocumentException | NumberFormatException e) {
            throw new ImportFileException("Error during Excel file processing. Import canceled");
        }

        return products;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product findProductByBrandAndCodeAndSupplierId(String brand, String code, UUID supplierId) {
        return productRepository.findProductByBrandAndCodeAndSupplierId(brand, code, supplierId);
    }

    @Override
    public List<ProductResponse> getAllByFilter(UUID supplierId, String brand, String filter) {
        List<Product> products = new ArrayList<>();

        if (supplierId == null) {
            // Supplier null && brand null && filter null
            if (brand == null && filter == null) {
                System.out.println("// Supplier null && brand null && filter null");
                products = productRepository.findAll();
            }
            // Supplier null && brand null && filter
            else if (brand == null && filter != null) {
                System.out.println("// Supplier null && brand null && filter");
                products = productRepository.findAllByBrandContainingOrModelContainingOrDescriptionContaining(filter);
            }
            // Supplier null && brand && filter null
            else if (brand != null && filter == null) {
                System.out.println("// Supplier null && brand && filter null");
                products = productRepository.findAllByBrand(brand);
            }
            // Supplier null && brand && filter
            else {
                System.out.println("// Supplier null && brand && filter");
                products = productRepository.findAllByBrandAndModelContainingOrDescriptionContaining(brand, filter);
            }
        }

        if (supplierId != null) {
            // Supplier && brand null && filter null
            if (brand == null && filter == null) {
                System.out.println("// Supplier && brand null && filter null");
                products = productRepository.findAllBySupplierId(supplierId);
            }
            // Supplier && brand null && filter
            else if (brand == null && filter != null) {
                System.out.println("// Supplier && brand null && filter");
                products = productRepository.findAllBySupplierIdAndBrandContainingOrModelContainingOrDescriptionContaining(supplierId, filter);
            }
            // Supplier && brand && filter null
            else if (brand != null && filter == null) {
                System.out.println("// Supplier && brand && filter null");
                products = productRepository.findAllBySupplierIdAndBrand(supplierId, brand);

            }
            // Supplier && brand && filter
            else {
                System.out.println("// Supplier && brand && filter");
                products = productRepository.findAllBySupplierIdAndBrandAndCodeContainingOrDescriptionContaining(supplierId, brand, filter);
            }
        }
        return products.stream().map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse toProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setSupplierId(product.getSupplierId());
        productResponse.setBrand(product.getBrand());
        productResponse.setCode(product.getCode());
        productResponse.setModel(product.getModel());
        productResponse.setDescription(product.getDescription());
        productResponse.setCategory(product.getCategory());
        productResponse.setLastUpdate(product.getLastUpdate());
        productResponse.setPrice(product.getPrice());
        productResponse.setSuggestedPrice(product.getSuggestedPrice());
        productResponse.setSuggestedWebPrice(product.getSuggestedWebPrice());
        productResponse.setStockAvailable(product.getStockAvailable());
        productResponse.setBarcode(product.getBarcode());
        productResponse.setCurrency(product.getCurrency());
        productResponse.setTaxRate(product.getTaxRate());

        return productResponse;
    }
}
