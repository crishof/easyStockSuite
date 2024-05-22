package org.crishof.pricesv.service;

import lombok.RequiredArgsConstructor;
import org.crishof.pricesv.dto.PriceRequest;
import org.crishof.pricesv.dto.PriceResponse;
import org.crishof.pricesv.exception.PriceNotFoundException;
import org.crishof.pricesv.model.Price;
import org.crishof.pricesv.modelMapper.PriceMapper;
import org.crishof.pricesv.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

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

    @Override
    public void updatePricesFromInvoice(UUID priceId, PriceRequest priceRequest) {

        System.out.println("priceId = " + priceId);
        System.out.println("priceRequest = " + priceRequest);
        Price price = priceRepository.getReferenceById(priceId);
        price.setPurchasePrice(priceRequest.getPurchasePrice());
        price.setTaxRate(priceRequest.getTaxRate());
        price.setDiscount(priceRequest.getDiscountRate());
        priceRepository.save(price);
    }
}
