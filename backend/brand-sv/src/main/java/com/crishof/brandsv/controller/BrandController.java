package com.crishof.brandsv.controller;

import com.crishof.brandsv.apiCient.ImageAPIClient;
import com.crishof.brandsv.exeption.BrandNotFoundExeption;
import com.crishof.brandsv.model.Brand;
import com.crishof.brandsv.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @Autowired
    ImageAPIClient imageAPIClient;

    @Value("${server.port}")
    private int serverPort;

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        try {
            brandService.deleteById(id);
            return ResponseEntity.ok("Brand successfully deleted desde back");
        } catch (BrandNotFoundExeption e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand not found: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());

        }
    }

    @PutMapping("/updateImage/{id}")
    public ResponseEntity<Brand> updateImage(@PathVariable("id") UUID uuid, @RequestParam("file") MultipartFile file) {

        try {
            byte[] fileBytes = file.getBytes();
            String mime = file.getContentType();
            String name = file.getOriginalFilename();

            ResponseEntity<String> responseEntity = imageAPIClient.saveImage(fileBytes, mime, name, Brand.class.getSimpleName());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String imageUrl = responseEntity.getBody();

                Brand updatedBrand = brandService.updateImage(uuid, imageUrl);
                return ResponseEntity.ok(updatedBrand);
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

