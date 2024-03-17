package com.crishof.brandsv.controller;

import com.crishof.brandsv.model.Brand;
import com.crishof.brandsv.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @Value("${server.port}")
    private int serverPort;

    @PostMapping("/save")
    public String saveBrand(@RequestBody Brand brand) {
        brandService.save(brand);
        return "Brand created successfully";
    }

    @GetMapping("/getById/{id}")
    public Brand getById(@PathVariable("id") Long id) {
        System.out.println("--------- PUERTO: " + serverPort);
        return brandService.findById(id);
    }

    @GetMapping("/getAll")
    public List<Brand> getAllBrands() {
        return brandService.findAll();
    }

    @PutMapping("/edit/{id}")
    public Brand editBrand(@PathVariable("id") Long id, @RequestBody Brand brand) {
        brandService.update(id, brand);
        return brandService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        brandService.deleteById(id);
        return "Brand successfully deleted";
    }

    @GetMapping("/findByName/{brand}")
    Long getBrandIdByName(@PathVariable("brand") String brand) {
        return brandService.getBrandIdByName(brand);
    }

    @PostMapping("/saveBrandName")
    void saveBrandName(@RequestBody String brand) {

        brandService.saveBrandName(brand);
    }
}
