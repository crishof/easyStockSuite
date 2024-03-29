package org.crishof.pricesv.controller;

import org.crishof.pricesv.dto.PriceRequest;
import org.crishof.pricesv.dto.PriceResponse;
import org.crishof.pricesv.exception.PriceNotFoundException;
import org.crishof.pricesv.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/price")
//@CrossOrigin(origins = "http://localhost:4200")
public class PriceController {

    @Autowired
    PriceService priceService;

    @GetMapping("/getById/{id}")
    public PriceResponse getById(@PathVariable("id") UUID id) {
        return priceService.getById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAndGetId(@RequestBody PriceRequest priceRequest) {

        try {
            return ResponseEntity.ok(priceService.saveAndGetId(priceRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
