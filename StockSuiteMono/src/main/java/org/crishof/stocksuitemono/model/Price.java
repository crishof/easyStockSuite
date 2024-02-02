package org.crishof.stocksuitemono.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private double purchasePrice = 0.0;
    private double sellingPrice = 0.0;
    private double taxRate = 0.0;
}
