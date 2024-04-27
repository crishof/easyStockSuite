package com.crishof.branchsv.controller;

import com.crishof.branchsv.dto.BranchRequest;
import com.crishof.branchsv.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBranches() {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getAllBranches());
    }

    @PostMapping("/createBranch")
    public ResponseEntity<?> createBranch(@RequestBody BranchRequest branchRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.createBranch(branchRequest));

    }

    @PutMapping("/updateBranch")
    public ResponseEntity<?> updateBranch(UUID branchId, BranchRequest branchRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.updateBranchName(branchId, branchRequest));
    }

    @PostMapping("/createLocation")
    public ResponseEntity<?> createLocation(UUID branchId, String name) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.createLocation(branchId, name));
    }

    @PutMapping("/updateLocation")
    public ResponseEntity<?> updateLocation(UUID branchId, UUID locationId, String name) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.updateLocation(branchId, locationId, name));
    }

    @DeleteMapping("/deleteLocation")
    public ResponseEntity<?> deleteLocation(UUID branchId, UUID locationId) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.deleteLocation(branchId, locationId));
    }
}
