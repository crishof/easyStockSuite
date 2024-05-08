package com.crishof.supplier.mapper;

import com.crishof.supplier.dto.SupplierResponse;
import com.crishof.supplier.model.Supplier;
import org.modelmapper.ModelMapper;

public class SupplierMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static SupplierResponse toSupplierResponse(Supplier supplier) {
        return modelMapper.map(supplier, SupplierResponse.class);
    }
}
