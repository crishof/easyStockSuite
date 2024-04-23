package com.crishof.brandsv.controller;

import com.crishof.brandsv.apiClient.ImageAPIClient;
import com.crishof.brandsv.dto.BrandRequest;
import com.crishof.brandsv.dto.BrandResponse;
import com.crishof.brandsv.exeption.BrandNotFoundException;
import com.crishof.brandsv.exeption.DuplicateNameException;
import com.crishof.brandsv.model.Brand;
import com.crishof.brandsv.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/brand")
public class BrandController {

    final
    BrandService brandService;

    final
    ImageAPIClient imageAPIClient;

    public BrandController(BrandService brandService, ImageAPIClient imageAPIClient) {
        this.brandService = brandService;
        this.imageAPIClient = imageAPIClient;
    }

    @Operation(summary = "Get All Brands")
    @GetMapping(path = "/getAll")
    public List<BrandResponse> getAll() {
        return brandService.getAll();
    }

    @Operation(summary = "Get Brand by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class))}),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Invalid Id",
                    content = @Content)})
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        try {
            BrandResponse brand = brandService.getById(id);
            return ResponseEntity.ok(brand);
        } catch (BrandNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @Operation(summary = "Get Brand Name by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand name found",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/getNameById")
    public ResponseEntity<String> getNameById(@RequestParam UUID uuid) {
        return ResponseEntity.ok(brandService.getById(uuid).getName());
    }

    @Operation(summary = "Get Brand by Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/getByName")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        try {
            BrandResponse brand = brandService.getByName(name);
            return ResponseEntity.ok(brand);
        } catch (BrandNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @Operation(summary = "Get Brand ID by Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand ID found",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/getIdByName")
    public ResponseEntity<?> getIdByName(@RequestParam String brandName) {
        try {
            BrandResponse brandResponse = brandService.getByName(brandName);
            return ResponseEntity.ok(brandResponse.getId());
        } catch (BrandNotFoundException e) {
            BrandRequest brandRequest = new BrandRequest(brandName);
            BrandResponse brandResponse = brandService.save(brandRequest);
            return ResponseEntity.ok(brandResponse.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/getAllIdByFilter")
    public ResponseEntity<?> getAllIdByFilter(@RequestParam String filter) {
        try {
            List<BrandResponse> brands = brandService.getAllByFilter(filter);
            List<UUID> brandsId = brands.stream().map(BrandResponse::getId)
                    .toList();

            if (brandsId.isEmpty()) {
                return ResponseEntity.ok(Collections.EMPTY_LIST);
            } else return ResponseEntity.ok(brandsId);
        } catch (BrandNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @Operation(summary = "Save Brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand saved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "text/plain")})
    })
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody BrandRequest brandRequest) {

        try {
            BrandResponse brandResponse = brandService.save(brandRequest);
            return ResponseEntity.ok(brandResponse);
        } catch (DuplicateNameException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @Operation(summary = "Update Brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "text/plain")})
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<BrandResponse> updateBrand(
            @PathVariable("id") UUID id,
            @RequestParam(required = false) String brandName,
            @RequestPart(required = false) MultipartFile file) {

        try {
            if (brandName == null && (file == null || file.isEmpty())) {
                return ResponseEntity.badRequest().build();
            }

            if (file != null && !file.isEmpty()) {

                byte[] fileBytes = file.getBytes();
                String mime = file.getContentType();
                String name = file.getOriginalFilename();
                ResponseEntity<String> responseEntity = imageAPIClient.saveImage(fileBytes, mime, name, Brand.class.getSimpleName());

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String imageUrl = responseEntity.getBody();
                    return ResponseEntity.ok().body(brandService.updateBrand(id, brandName, imageUrl));
                } else {
                    return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
                }
            } else {
                return ResponseEntity.ok().body(brandService.updateBrandName(id, brandName));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update Brand Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand name updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "text/plain")})
    })
    @PutMapping("/updateBrandName/{id}")
    public BrandResponse updateBrandName(@PathVariable("id") UUID id, @RequestParam(required = false) String name) {
        return brandService.updateBrandName(id, name);
    }

    @Operation(summary = "Update Brand Image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand image updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "text/plain")})
    })
    @PutMapping("/updateImage/{id}")
    public ResponseEntity<BrandResponse> updateImage(@PathVariable("id") UUID id, @RequestParam("file") MultipartFile file) {

        try {
            byte[] fileBytes = file.getBytes();
            String mime = file.getContentType();
            String name = file.getOriginalFilename();

            ResponseEntity<String> responseEntity = imageAPIClient.saveImage(fileBytes, mime, name, Brand.class.getSimpleName());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String imageUrl = responseEntity.getBody();

                BrandResponse updatedBrand = brandService.updateImage(id, imageUrl);
                return ResponseEntity.ok(updatedBrand);
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete Brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand successfully deleted",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "text/plain")})
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        try {
            brandService.deleteById(id);
            return ResponseEntity.ok("Brand successfully deleted");
        } catch (BrandNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand not found: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());

        }
    }
}

