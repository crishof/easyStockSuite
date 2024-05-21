package org.crishof.addresssv.service;

import org.crishof.addresssv.dto.AddressRequest;
import org.crishof.addresssv.dto.AddressResponse;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    List<AddressResponse> findAllAddresses();

    AddressResponse findAddressById(UUID addressId);

    AddressResponse saveAddress(AddressRequest addressRequest);

    AddressResponse updateAddress(UUID addressId, AddressRequest addressRequest);

    String deleteAddress(UUID addressId);
}
