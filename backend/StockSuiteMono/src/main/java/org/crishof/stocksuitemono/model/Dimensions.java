package org.crishof.stocksuitemono.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dimensions {
    private double length = 0.0;
    private double width = 0.0;
    private double height = 0.0;
    private double weight = 0.0;
}
