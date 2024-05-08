package org.crishof.invoicesv.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crishof.invoicesv.apiClient.ProductAPIClient;
import org.crishof.invoicesv.apiClient.StockApiClient;
import org.crishof.invoicesv.dto.InvoiceRequest;
import org.crishof.invoicesv.dto.InvoiceResponse;
import org.crishof.invoicesv.exception.InvoiceNotFoundException;
import org.crishof.invoicesv.model.Invoice;
import org.crishof.invoicesv.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("invoiceService")
@Slf4j
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductAPIClient productAPIClient;
    private final StockApiClient stockApiClient;

    @Override
    public List<InvoiceResponse> getAll() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        return invoiceList.stream().map(InvoiceResponse::new).toList();
    }

    @Override
    public InvoiceResponse getById(UUID id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(InvoiceNotFoundException::new);

        return this.toInvoiceResponse(invoice);
    }

    private InvoiceResponse toInvoiceResponse(Invoice invoice) {

        return InvoiceResponse.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .issueDate(invoice.getIssueDate())
                .receptionDate(invoice.getReceptionDate())
                .build();
    }

//    @Override
//    @Transactional
//    public void save(InvoiceRequest invoiceRequest) {
//
//        Invoice invoice = new Invoice(invoiceRequest);
//
//        switch (invoiceRequest.getTransactionType()) {
//
//            case SALE, TRANSFER -> {
//
//                for (OrderProductsRequest product : invoiceRequest.getProductList()) {

//                    confirmar stock
//                    modificar stock del producto
//                    guardar stock modificado
//                    int requiredUnits = entry.getValue();

//                    ProductRequest product = (ProductRequest) productAPIClient.getById(productId).getBody();
//                    int existingUnits = stockApiClient.getStockById(product.getStockIds().get(0));
//                    if (existingUnits < requiredUnits) {
//                        throw new IllegalStateException("Not enough units in stock for product id: " + product.getId() + " - STOCK = " + existingUnits);
//                    }
//
//                    Stock stock = stockService.save(product, entry.getValue() * -1);
//                    product.getStocks().add(stock);
//                }
//            }
//            case PURCHASE, RETURN -> {
//
//                for (Map.Entry<UUID, Integer> entry : invoiceRequest.getProductList().entrySet()) {
//                    UUID productId = entry.getKey();
//                    ProductRequest product = productService.getProductById(productId);
//                    Stock stock = stockService.save(product, entry.getValue());
//                    product.getStocks().add(stock);
//                }
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + invoiceRequest.getTransactionType());
//        }
//
//        invoiceRepository.save(invoice);
//    }


    @Override
    @Transactional
    public InvoiceResponse update(UUID id, InvoiceRequest invoiceRequest) {
        Invoice invoice = invoiceRepository.getReferenceById(id);
        invoice.updateFromRequest(invoiceRequest);
        return new InvoiceResponse(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        invoiceRepository.deleteById(id);
    }
}