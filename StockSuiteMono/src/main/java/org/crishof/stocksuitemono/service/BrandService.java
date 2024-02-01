package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> findAll();

    Brand findtById(Long id);

    void save(Brand brand);

    Brand update(Long id, Brand brand);

    void deleteById(Long id);

}
