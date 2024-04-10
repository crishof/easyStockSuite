package org.crishof.pricesv.service;

import org.crishof.pricesv.dto.PriceRequest;
import org.crishof.pricesv.dto.PriceResponse;
import org.crishof.pricesv.exception.PriceNotFoundException;
import org.crishof.pricesv.model.Price;
import org.crishof.pricesv.modelMapper.PriceMapper;
import org.crishof.pricesv.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepository priceRepository;

    @Override
    public PriceResponse getById(UUID uuid) {

        Price price = priceRepository.findById(uuid
        ).orElseThrow(() -> new PriceNotFoundException(uuid));

        return PriceMapper.toPriceResponse(price);
    }

    @Override
    public UUID saveAndGetId(PriceRequest priceRequest) {

        Price price = new Price(priceRequest);

        return priceRepository.save(price).getUuid();
    }
}
