package org.crishof.stocksuitemono.controller;


import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("/findAll")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Brand findById(@PathVariable("id") Long id) {
        return brandService.findtById(id);
    }

    @PostMapping("/save")
    public String save(@RequestBody Brand brand) {
        brandService.save(brand);

        return "Brand successfully saved";
    }

    @PutMapping("/edit/{id}")
    public Brand editBrand(@RequestParam("id") Long id, @RequestBody Brand brand) {
        brandService.update(id, brand);
        return brandService.findtById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        brandService.deleteById(id);
        return "Brand successfully deleted";
    }
}

