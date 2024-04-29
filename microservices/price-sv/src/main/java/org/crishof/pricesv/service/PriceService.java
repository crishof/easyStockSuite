package org.crishof.pricesv.service;

import org.crishof.pricesv.dto.PriceRequest;
import org.crishof.pricesv.dto.PriceResponse;

import java.util.UUID;

public interface PriceService {

    PriceResponse getById(UUID uuid);

    UUID saveAndGetId(PriceRequest priceRequest);

    void updatePricesFromInvoice(UUID priceId, PriceRequest priceRequest);
}
