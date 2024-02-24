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

    //    1 - Crear nueva marca
    @PostMapping("/save")
    public String saveBrand(@RequestBody Brand brand) {
        brandService.save(brand);
        return "Brand created successfully";
    }

    //    2 - Obtener una marca
    @GetMapping("/getById/{id}")
    public Brand getById(@PathVariable("id") Long id) {
        System.out.println("--------- PUERTO: " + serverPort);
        return brandService.findById(id);
    }

    //    3 - Obtener todas las marcas
    @GetMapping("/getAll")
    public List<Brand> getAllBrands() {
        return brandService.findAll();
    }

    //    4 - Editar una marca
    @PutMapping("/edit/{id}")
    public Brand editBrand(@PathVariable("id") Long id, @RequestBody Brand brand) {
        brandService.update(id, brand);
        return brandService.findById(id);
    }

    //    5 - Eliminar una marca
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
