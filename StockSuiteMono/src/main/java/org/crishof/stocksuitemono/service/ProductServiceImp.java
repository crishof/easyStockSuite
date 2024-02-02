package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findtById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductRequest productRequest) {

        Product product = new Product();

        product.setCode(productRequest.getCode());
        product.setModel(productRequest.getModel());
        product.setDescription(productRequest.getDescription());

        Brand brand = brandService.saveByName(productRequest.getBrandName());

        product.setBrandId(brand.getId());

        productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {

        Product product1 = this.findtById(id);

        product1.setCode(product.getCode());
        product1.setModel(product.getModel());
        product1.setDescription(product.getDescription());
        product1.setBrandId(product.getBrandId());
        product1.setCategoryId(product.getCategoryId());
        product1.getPrice().setSellingPrice(product.getPrice().getSellingPrice());
        product1.getPrice().setPurchasePrice(product.getPrice().getPurchasePrice());
        product1.getPrice().setTaxRate(product.getPrice().getTaxRate());

        return productRepository.save(product1);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
