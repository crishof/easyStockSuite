package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Brand;

import java.util.List;
import java.util.UUID;

public interface BrandService {

    List<Brand> getAll();

    Brand getById(UUID id);

    Brand save(Brand brand);

    Brand update(UUID id, Brand brand);

    void deleteById(UUID id);

    Brand getByName(String name);

    Brand saveByName(String brandName);
}
