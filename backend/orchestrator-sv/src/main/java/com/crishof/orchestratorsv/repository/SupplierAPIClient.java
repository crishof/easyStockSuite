package com.crishof.orchestratorsv.repository;

import com.crishof.orchestratorsv.dto.SupplierDTO;
import com.crishof.orchestratorsv.model.Supplier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "supplier-sv")
public interface SupplierAPIClient {
    @GetMapping("/supplier/getById/{supplierId}")
    SupplierDTO getSupplierInfo(@PathVariable("supplierId") Long id);

    @PostMapping("/supplier/save")
    String save(Supplier supplier);

    @GetMapping("/supplier/findByName/{supplier}")
    Long getSupplierIdByName(@PathVariable("supplier") String supplier);

    @PostMapping("/supplier/saveSupplierName")
    void saveSupplierName(@RequestBody String supplier);
}