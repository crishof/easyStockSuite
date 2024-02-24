package org.crishof.stocksuitemono.service;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.exception.IOException.ImportFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ImportFileServiceImpl implements ImportFileService {

    public List<ProductRequest> readExcel(MultipartFile file) {
        List<ProductRequest> products = new ArrayList<>();

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

                    // Crea un nuevo objeto ProductRequest por cada fila
                    ProductRequest product = new ProductRequest();

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
                                case "brandname":
                                    product.setBrandName(cellContent);
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
                                case "purchaseprice", "taxrate", "sellingprice":

                                    // Elimina caracteres no deseados, como comas
                                    cellContent = cellContent.replaceAll(",", "").replaceAll(" ", "");

                                    // Asigna el valor convertido a double
                                    double numericValue = Double.parseDouble(cellContent);

                                    // Asigna el valor al atributo correspondiente

                                    switch (title) {
                                        case "purchaseprice":
                                            System.out.println("numericValue = " + numericValue);
                                            product.setPurchasePrice(numericValue);
                                            System.out.println("product = " + product.getPurchasePrice());
                                            break;
                                        case "taxrate":
                                            product.setTaxRate(numericValue);
                                            break;
                                        case "sellingprice":
                                            product.setSellingPrice(numericValue);
                                            break;
                                    }
                            }
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
}
