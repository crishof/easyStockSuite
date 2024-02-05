package org.crishof.stocksuitemono.service;

import jakarta.persistence.EntityNotFoundException;
import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.dto.ProductResponse;
import org.crishof.stocksuitemono.exception.notFound.ProductNotFoundException;
import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.model.Price;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.model.Supplier;
import org.crishof.stocksuitemono.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SupplierService supplierService;

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(ProductResponse::new).toList();
    }

    @Override
    public ProductResponse getById(UUID id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductResponse(product);
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) throws NullPointerException {

        Product product = new Product(productRequest);
        product.setPrice(new Price());
        Supplier supplier = supplierService.findByName(productRequest.getSupplier());
//        product.setSupplierId(supplier.getId());

        product.setCode(productRequest.getCode());
        product.setModel(productRequest.getModel());
        product.setDescription(productRequest.getDescription());
        product.getPrice().setPurchasePrice(productRequest.getPurchasePrice());
        product.getPrice().setSellingPrice(productRequest.getSellingPrice());
        product.getPrice().setTaxRate(productRequest.getTaxRate());

        Brand brand = brandService.saveByName(productRequest.getBrandName());
        product.setBrand(brand);

        Product saved = productRepository.save(product);
        return new ProductResponse(saved);
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest productRequest) throws NullPointerException {

        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.setModel(productRequest.getModel());
        product.setDescription(productRequest.getDescription());
        return new ProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> getAllByFilter(String filter) {
        List<Product> products = new ArrayList<>();
        products.addAll(productRepository.findAllByBrandName(filter));
        products.addAll(productRepository.findAllByModelContainingIgnoreCase(filter));
        products.addAll(productRepository.findAllByDescriptionContainingIgnoreCase(filter));

        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllByFilterWithStock(String filter) {

        List<ProductResponse> productResponses = this.getAllByFilter(filter);
        return null;
    }
}
