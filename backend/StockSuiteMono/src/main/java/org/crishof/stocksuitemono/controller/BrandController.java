package org.crishof.stocksuitemono.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/brand")
@CrossOrigin(origins = "http://localhost:4200")
public class BrandController {

    @Autowired
    BrandService brandService;

    @Operation(summary = "Get All Brands")
    @GetMapping(path = "/getAll")
    public List<Brand> getAll() {
        return brandService.getAll();
    }

    @Operation(summary = "Get Brand by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Brand found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class))}), @ApiResponse(responseCode = "500", description = "Invalid Id", content = @Content)})
    @GetMapping("/getById/{id}")
    public Brand getById(@Parameter(description = "Brand id", example = "87194fd9-de0b-44de-8fcc-b5819c7c94aa") @PathVariable("id") UUID id) {
        return brandService.getById(id);
    }

    @PostMapping("/save")
    public Brand save(@RequestBody Brand brand) {
        return brandService.save(brand);
    }

    @PutMapping("/update/{id}")
    public Brand updateBrand(@PathVariable("id") UUID id, @RequestParam(required = false) String name /*, @RequestPart(required = false) MultipartFile logo */) {

//        System.out.println("name = " + name);
//        if (logo == null) {
//            System.out.println("LOGO VACIO");
//        }
        return brandService.update(id, name /*,  logo */);
    }

    @PutMapping("/updateLogo/{id}")
    public Brand updateLogo(@PathVariable("id") UUID uuid, @RequestParam(required = false) MultipartFile logo) {

        return brandService.updateLogo(uuid, logo);

    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id) {
        brandService.deleteById(id);
        return "Brand successfully deleted";
    }
}

