package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> getAll();

    Brand getById(Long id);

    Brand save(Brand brand);

    Brand update(Long id, Brand brand);

    void deleteById(Long id);

    Brand getBrandByName(String name);

    Brand saveByName(String brandName);
}
