package org.crishof.addresssv.controller;

import lombok.RequiredArgsConstructor;
import org.crishof.addresssv.dto.AddressRequest;
import org.crishof.addresssv.dto.AddressResponse;
import org.crishof.addresssv.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/getAll")
    public ResponseEntity<List<AddressResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAllAddresses());
    }

    @GetMapping("/getById")
    public ResponseEntity<AddressResponse> getById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAddressById(id));
    }

    @PutMapping("/update/{addressId}")
    public ResponseEntity<AddressResponse> update(@PathVariable UUID addressId, @RequestBody AddressRequest addressRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.updateAddress(addressId, addressRequest));
    }

    @PostMapping("/save")
    public ResponseEntity<AddressResponse> save(@RequestBody AddressRequest addressRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.saveAddress(addressRequest));
    }

    @PostMapping("/createAndGetId")
    public ResponseEntity<UUID> createAndGetId(@RequestBody(required = false) AddressRequest addressRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.saveAddress(addressRequest).getId());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.deleteAddress(id));
    }
}
