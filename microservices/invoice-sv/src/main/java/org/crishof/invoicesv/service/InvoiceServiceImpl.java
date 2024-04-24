package org.crishof.invoicesv.service;

import jakarta.transaction.Transactional;
import org.crishof.invoicesv.apiClient.ProductAPIClient;
import org.crishof.invoicesv.dto.InvoiceRequest;
import org.crishof.invoicesv.dto.InvoiceResponse;
import org.crishof.invoicesv.exception.InvoiceNotFoundException;
import org.crishof.invoicesv.model.Invoice;
import org.crishof.invoicesv.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.crishof.invoicesv.enums.TransactionType.*;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    ProductAPIClient productAPIClient;

    @Override
    public List<InvoiceResponse> getAll() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        return invoiceList.stream().map(InvoiceResponse::new).toList();
    }

    @Override
    public InvoiceResponse getById(UUID id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(InvoiceNotFoundException::new);

        return new InvoiceResponse(invoice);
    }

    @Override
    @Transactional
    public void save(InvoiceRequest invoiceRequest) {

        Invoice invoice = new Invoice(invoiceRequest);
        switch (invoiceRequest.getTransactionType()) {

            case SALE, TRANSFER -> {

                for (Map.Entry<UUID, Integer> entry : invoiceRequest.getProductList().entrySet()) {

                    UUID productId = entry.getKey();
                    int requiredUnits = entry.getValue();

//                    Product product = productAPIClient.getById(productId);
//                    int existingUnits = stockService.getStockForProduct(productId);
//                    if (existingUnits < requiredUnits) {
//                        throw new IllegalStateException("Not enough units in stock for product id: " + product.getId() + " - STOCK = " + existingUnits);
//                    }
//
//                    Stock stock = stockService.save(product, entry.getValue() * -1);
//                    product.getStocks().add(stock);
//                }
//            }
//            case PURCHASE, RETURN -> {

//                for (Map.Entry<UUID, Integer> entry : invoiceRequest.getProductList().entrySet()) {
//                    UUID productId = entry.getKey();
//                    Product product = productService.getProductById(productId);
//                    Stock stock = stockService.save(product, entry.getValue());
//                    product.getStocks().add(stock);
//                }
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + invoiceRequest.getTransactionType());
//        }

//        invoiceRepository.save(invoice);
//    }


//    @Override
//    @Transactional
//    public InvoiceResponse update(UUID id, InvoiceRequest invoiceRequest) {
//        Invoice invoice = invoiceRepository.getReferenceById(id);
//        invoice.updateFromRequest(invoiceRequest);
//        return new InvoiceResponse(invoiceRepository.save(invoice));
//    }

//    @Override
//    @Transactional
//    public void deleteById(UUID id) {
//        invoiceRepository.deleteById(id);
//    }
                }
            }
        }
    }
}