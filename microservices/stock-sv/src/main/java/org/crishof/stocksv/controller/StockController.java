package org.crishof.stocksv.controller;

import lombok.RequiredArgsConstructor;
import org.crishof.stocksv.dto.StockRequest;
import org.crishof.stocksv.dto.StockResponse;
import org.crishof.stocksv.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private static final String SERVER_ERROR = "Internal server error";
    private final StockService stockService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody StockRequest stockRequest) {

        try {
            StockResponse stockResponse = stockService.save(stockRequest);
            return ResponseEntity.ok(stockResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVER_ERROR);
        }
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<?> updateQuantity(@PathVariable("stockId") UUID stockId, @RequestBody StockRequest stockRequest) {
        try {
            StockResponse stockResponse = stockService.updateQuantity(stockId, stockRequest);
            return ResponseEntity.ok(stockResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{stockId}")
    public ResponseEntity<?> getById(@PathVariable("stockId") UUID stockId) {

        try {
            StockResponse stockResponse = stockService.getStockById(stockId);
            return ResponseEntity.ok(stockResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVER_ERROR);
        }
    }

    @GetMapping("/getTotalStockForProduct")
    public ResponseEntity<?> getTotalStockForProduct(@RequestParam("stockIdList") List<UUID> stockIdList) {
        try {
            int totalStockForProduct = stockService.getTotalStockForProduct(stockIdList);
            return ResponseEntity.ok(totalStockForProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVER_ERROR);
        }
    }

    @GetMapping("/getStockById")
    public ResponseEntity<?> getStockById(UUID stockId) {
        return ResponseEntity.status(HttpStatus.OK).body(stockService.getStockById(stockId));
    }
}
