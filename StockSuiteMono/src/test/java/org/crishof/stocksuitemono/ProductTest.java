package org.crishof.stocksuitemono;

import org.crishof.stocksuitemono.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {

    }


}
