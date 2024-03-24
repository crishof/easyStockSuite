package com.crishof.brandsv.controller;

import com.crishof.brandsv.apiCient.ImageAPIClient;
import com.crishof.brandsv.dto.BrandRequest;
import com.crishof.brandsv.dto.BrandResponse;
import com.crishof.brandsv.exeption.BrandNotFoundException;
import com.crishof.brandsv.model.Brand;
import com.crishof.brandsv.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @Autowired
    ImageAPIClient imageAPIClient;

    @Operation(summary = "Get All Brands")
    @GetMapping(path = "/getAll")
    public List<BrandResponse> getAll() {
        return brandService.getAll();
    }

    @Operation(summary = "Get Brand by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Brand found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class))}), @ApiResponse(responseCode = "500", description = "Invalid Id", content = @Content)})
    @GetMapping("/getById/{id}")
    public BrandResponse getById(@Parameter(description = "Brand id", example = "87194fd9-de0b-44de-8fcc-b5819c7c94aa") @PathVariable("id") UUID id) {
        return brandService.getById(id);
    }

    @PostMapping("/save")
    public BrandResponse save(@RequestBody BrandRequest brandRequest) {

        return brandService.save(brandRequest);
    }

    @PutMapping("/update/{id}")
    public BrandResponse updateBrand(@PathVariable("id") UUID id, @RequestParam(required = false) String name) {
        return brandService.update(id, name);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        try {
            brandService.deleteById(id);
            return ResponseEntity.ok("Brand successfully deleted desde back");
        } catch (BrandNotFoundException e) {
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

                Brand updatedCategory = brandService.updateImage(uuid, imageUrl);
                return ResponseEntity.ok(updatedCategory);
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}

