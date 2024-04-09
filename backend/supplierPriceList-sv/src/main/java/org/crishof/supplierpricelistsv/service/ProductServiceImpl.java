package org.crishof.supplierpricelistsv.service;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.crishof.supplierpricelistsv.dto.ProductResponse;
import org.crishof.supplierpricelistsv.exception.ImportFileException;
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
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    final
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> readExcel(MultipartFile file) {
        List<Product> products = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del archivo

            // Verifica si hay al menos dos filas en la hoja
            if (sheet.getPhysicalNumberOfRows() < 2) {
                // No hay suficientes filas para procesar
                throw new RuntimeException("The file does not have enough rows to process.");
            }

            // Obtén la fila de títulos (fila 1)
            Row titleRow = sheet.getRow(0);
            List<String> titles = new ArrayList<>();

            // Itera sobre las celdas de la fila de títulos y almacena los títulos en minúsculas
            for (Cell titleCell : titleRow) {
                titles.add(titleCell.getStringCellValue().toLowerCase());

                System.out.println("titles = " + titles);
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
                            String title = titles.get(columnIndex).toLowerCase();

                            System.out.println("title = " + title);

                            int maxLength = 255;
                            double numericValue;

                            //TODO - Error cuando hay celdas vacías

                            // Asigna el valor de la celda al atributo correspondiente según el título
                            switch (title) {
                                case "brand":
                                    System.out.println("brand = " + cellContent);
                                    product.setBrand(cellContent);
                                    break;
                                case "code":
                                    System.out.println("code = " + cellContent);
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        product.setCode(cellContent);
                                    } else product.setCode(null);
                                    break;
                                case "model":
                                    System.out.println("model = " + cellContent);
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        product.setModel(cellContent);
                                    } else product.setModel(null);
                                    break;
                                case "description":
                                    System.out.println("description = " + cellContent);
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        if (cellContent.length() > maxLength) {
                                            cellContent = cellContent.substring(0, maxLength);
                                        }
                                        product.setDescription(cellContent);

                                    } else product.setDescription(null);
                                    break;
                                case "category":
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        System.out.println("category = " + cellContent);
                                        product.setCategory(cellContent);
                                    } else product.setCategory(null);
                                    break;
                                case "price":
                                    System.out.println("cell before= " + cellContent);
//                                     Elimina caracteres no deseados, como comas
                                    cellContent = cellContent.replaceAll(",", "").replaceAll(" ", "");
                                    System.out.println("cell after= " + cellContent);

                                    // Asigna el valor convertido a double
                                    numericValue = Double.parseDouble(cellContent);
                                    System.out.println("price = " + numericValue);
                                    product.setPrice(numericValue);
                                    break;

                                case "tax-rate":
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        System.out.println("cell before= " + cellContent);
                                        // Elimina caracteres no deseados, como comas y espacios
                                        cellContent = cellContent.replaceAll(",", "").replaceAll(" ", "");

                                        if (cellContent.endsWith("%")) {
                                            // Elimina el signo de porcentaje al final de la cadena
                                            cellContent = cellContent.substring(0, cellContent.length() - 1);
                                            // Convierte el valor numérico a decimal dividiéndolo por 100
                                            numericValue = Double.parseDouble(cellContent) / 100.0;
                                        } else {
                                            // Si no termina con "%", lo convierte a double
                                            numericValue = Double.parseDouble(cellContent);
                                        }

                                        System.out.println("tax-rate = " + numericValue);
                                        product.setTaxRate(numericValue);
                                    } else product.setTaxRate(0.21);
                                    break;

                                case "suggested-price":
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        System.out.println("cell before= " + cellContent);
//                                     Elimina caracteres no deseados, como comas
                                        cellContent = cellContent.replaceAll(",", "").replaceAll(" ", "");
                                        System.out.println("cell after= " + cellContent);

                                        // Asigna el valor convertido a double
                                        numericValue = Double.parseDouble(cellContent);
                                        System.out.println("suggested-price = " + numericValue);
                                        product.setSuggestedPrice(numericValue);
                                    } else product.setSuggestedPrice(0);
                                    break;

                                case "suggested-web-price":
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        System.out.println("cell before= " + cellContent);
//                                     Elimina caracteres no deseados, como comas
                                        cellContent = cellContent.replaceAll(",", "").replaceAll(" ", "");
                                        System.out.println("cell after= " + cellContent);

//                                    Asigna el valor convertido a double
                                        numericValue = Double.parseDouble(cellContent);
                                        System.out.println("suggested-web-price = " + numericValue);
                                        product.setSuggestedWebPrice(numericValue);
                                    } else product.setSuggestedWebPrice(0);
                                    break;

                                case "stock":
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        System.out.println("stock = " + cellContent);
                                        product.setStockAvailable(cellContent);
                                    } else product.setStockAvailable(null);
                                    break;
                                case "bar-code":
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        System.out.println("bar-code = " + cellContent);
                                        product.setBarcode(cellContent);
                                    } else product.setBarcode(null);
                                    break;
                                case "currency":
                                    if (!cellContent.isBlank() || !cellContent.isEmpty()) {
                                        System.out.println("currency = " + cellContent);
                                        product.setCurrency(cellContent);
                                    } else product.setCurrency("$");
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
            else if (brand == null) {
                System.out.println("// Supplier null && brand null && filter");
                products = productRepository.findAllByBrandContainingOrModelContainingOrDescriptionContaining(filter);
            }
            // Supplier null && brand && filter null
            else if (filter == null) {
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
            else if (brand == null) {
                System.out.println("// Supplier && brand null && filter");
                products = productRepository.findAllBySupplierIdAndBrandContainingOrModelContainingOrDescriptionContaining(supplierId, filter);
            }
            // Supplier && brand && filter null
            else if (filter == null) {
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
