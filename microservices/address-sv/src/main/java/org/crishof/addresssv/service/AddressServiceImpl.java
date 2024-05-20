package org.crishof.addresssv.service;

import lombok.RequiredArgsConstructor;
import org.crishof.addresssv.dto.AddressRequest;
import org.crishof.addresssv.dto.AddressResponse;
import org.crishof.addresssv.exception.AddressNotFoundException;
import org.crishof.addresssv.model.Address;
import org.crishof.addresssv.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<AddressResponse> findAllAddresses() {
        return addressRepository.findAll().stream().map(this::toAddressResponse).toList();
    }

    @Override
    public AddressResponse findAddressById(UUID addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        return toAddressResponse(address);

    }

    @Override
    public AddressResponse saveAddress(AddressRequest addressRequest) {
        Address address = this.toAddress(addressRequest);
        return this.toAddressResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse updateAddress(UUID addressId, AddressRequest addressRequest) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setHouseNumber(addressRequest.getHouseNumber());
        address.setPostalCode(addressRequest.getPostalCode());
        return this.toAddressResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse deleteAddress(UUID addressId) {
        return null;
    }

    private AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }

    private Address toAddress(AddressRequest address) {
        return Address.builder()
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }
}
