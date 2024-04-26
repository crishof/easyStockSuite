package com.crishof.branchsv.controller;

import com.crishof.branchsv.dto.BranchRequest;
import com.crishof.branchsv.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    //crea nueva branch

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody BranchRequest branchRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(branchService.createBranch(branchRequest));

    }

    // actualiza nombre de branch

    // agrega location

    // elimina location


}
