package org.crishof.customersv.apiClient;

import org.crishof.customersv.dto.AddressRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "address-sv", url = "http://localhost:9010/address")
public interface AddressAPIClient {

    @PostMapping("/createAndGetId")
    ResponseEntity<UUID> createAndGetId(@RequestBody(required = false) AddressRequest addressRequest);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> delete(@PathVariable UUID id);
}
