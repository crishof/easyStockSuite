package org.crishof.stocksuitemono.controller;


import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("/getAll")
    public List<Brand> getAll() {
        return brandService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Brand getById(@PathVariable("id") UUID id) {
        return brandService.getById(id);
    }

    @PostMapping("/save")
    public Brand save(@RequestBody Brand brand) {
        return brandService.save(brand);
    }

    @PutMapping("/update/{id}")
    public Brand updateBrand(@PathVariable("id") UUID id, @RequestBody Brand brand) {
        return brandService.update(id, brand);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id) {
        brandService.deleteById(id);
        return "Brand successfully deleted";
    }
}

