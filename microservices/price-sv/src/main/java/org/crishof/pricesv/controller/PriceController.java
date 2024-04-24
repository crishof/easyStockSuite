package org.crishof.pricesv.controller;

import lombok.RequiredArgsConstructor;
import org.crishof.pricesv.dto.PriceRequest;
import org.crishof.pricesv.dto.PriceResponse;
import org.crishof.pricesv.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

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

    @PutMapping("/update/{priceId}")
    public ResponseEntity<?> update(@PathVariable("priceId") UUID priceId, @RequestBody PriceRequest priceRequest) {

        priceService.updatePricesFromInvoice(priceId, priceRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Prices updated successfully");
    }
}
