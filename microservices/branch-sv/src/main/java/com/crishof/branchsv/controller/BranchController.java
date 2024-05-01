package com.crishof.branchsv.controller;

import com.crishof.branchsv.dto.BranchRequest;
import com.crishof.branchsv.dto.BranchResponse;
import com.crishof.branchsv.exception.BranchNotFoundException;
import com.crishof.branchsv.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/getAll")
    public ResponseEntity<List<BranchResponse>> getAllBranches() {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getAllBranches());
    }

    @PostMapping("/createBranch")
    public ResponseEntity<BranchResponse> createBranch(@RequestBody BranchRequest branchRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.createBranch(branchRequest));

    }

    @PutMapping("/updateBranch/{branchId}")
    public ResponseEntity<BranchResponse> updateBranch(@PathVariable(name = "branchId") UUID branchId, @RequestBody BranchRequest branchRequest) throws BranchNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(branchService.updateBranchName(branchId, branchRequest));
        } catch (BranchNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error" + e.getMessage());
        }
    }

    @PostMapping("/createLocation/{branchId}")
    public ResponseEntity<BranchResponse> createLocation(@PathVariable(name = "branchId") UUID branchId, @RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.createLocation(branchId, name));
    }

    @PutMapping("/updateLocation/{branchId}")
    public ResponseEntity<BranchResponse> updateLocation(@PathVariable UUID branchId, @RequestParam UUID locationId, @RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.updateLocation(branchId, locationId, name));
    }

    @DeleteMapping("/deleteLocation/{branchId}")
    public ResponseEntity<BranchResponse> deleteLocation(@PathVariable(name = "branchId") UUID branchId, @RequestParam UUID locationId) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.deleteLocation(branchId, locationId));
    }
}
