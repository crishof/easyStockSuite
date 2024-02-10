package org.crishof.stocksuitemono.service;

import jakarta.transaction.Transactional;
import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.dto.InvoiceResponse;
import org.crishof.stocksuitemono.exception.notFound.InvoiceNotFoundException;
import org.crishof.stocksuitemono.model.Invoice;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.model.Stock;
import org.crishof.stocksuitemono.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    StockService stockService;

    @Override
    public List<InvoiceResponse> getAll() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        return invoiceList.stream().map(InvoiceResponse::new).toList();
    }

    @Override
    public InvoiceResponse getById(UUID id) {
        Invoice invoice = invoiceRepository.getReferenceById(id);
        if (invoice != null) {
            return new InvoiceResponse(invoice);
        }else
            throw new InvoiceNotFoundException(id);
    }

    @Override
    @Transactional
    public void save(InvoiceRequest invoiceRequest) {

        Invoice invoice = new Invoice(invoiceRequest);
        switch (invoiceRequest.getTransactionType()) {

            case SALE, TRANSFER -> {

                int count = 0;
                for (Product product : invoiceRequest.getProductList()) {
                    Stock stock = stockService.save(product, invoiceRequest.getQuantities().get(count) * -1);
                    product.getStocks().add(stock);
                    count++;
                }
            }
            case PURCHASE, RETURN -> {
                int count = 0;
                for (Product product : invoiceRequest.getProductList()) {
                    Stock stock = stockService.save(product, invoiceRequest.getQuantities().get(count));
                    product.getStocks().add(stock);
                    count++;
                }
            }

            default -> throw new IllegalStateException("Unexpected value: " + invoiceRequest.getTransactionType());
        }
        invoiceRepository.save(invoice);

    }

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