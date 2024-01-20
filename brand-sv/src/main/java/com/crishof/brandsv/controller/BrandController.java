package com.crishof.brandsv.controller;

import com.crishof.brandsv.model.Brand;
import com.crishof.brandsv.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("/getAll")
    public List<Brand> getAllBrands() {
        return brandService.findAll();
    }

    @PostMapping("/save")
    public String saveBrand(@RequestBody Brand brand) {
        brandService.save(brand);
        return "Brand saved successfully";
    }


}
