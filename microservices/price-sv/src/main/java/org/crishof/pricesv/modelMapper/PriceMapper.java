package org.crishof.pricesv.modelMapper;

import org.crishof.pricesv.dto.PriceResponse;
import org.crishof.pricesv.model.Price;
import org.modelmapper.ModelMapper;

public class PriceMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static PriceResponse toPriceResponse(Price category) {
        return modelMapper.map(category, PriceResponse.class);
    }
}
