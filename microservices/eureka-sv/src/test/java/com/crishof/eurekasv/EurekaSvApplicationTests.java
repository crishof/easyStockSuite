package com.crishof.eurekasv;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EurekaSvApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        // Ejecuta el método main para que se cubra en JaCoCo
        EurekaSvApplication.main(new String[]{});
    }
}