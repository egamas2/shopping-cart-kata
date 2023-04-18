package core.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@EqualsAndHashCode(of = {"name"})
@Getter
public class Product implements CartItem<Product>{
    private final String name;
    private final Long costInCents;
    private final Double revenue;
    private final Double tax;

    public Product(String name, Long costInCents, Double revenue, Double tax) {
        this.name = name;
        this.costInCents = costInCents;
        this.revenue = revenue;
        this.tax = tax;
    }

    public Double getPriceWithVAT() {
        BigDecimal applyRevenue = BigDecimal.valueOf(costInCents)
                .multiply(
                        BigDecimal.valueOf(100).add(
                                BigDecimal.valueOf(revenue)
                        )
                ).divide(BigDecimal.valueOf(10000), 2, RoundingMode.UP);
        return applyRevenue
                .multiply(
                        BigDecimal.valueOf(100).add(
                                BigDecimal.valueOf(tax)
                        )
                ).divide(BigDecimal.valueOf(100), 2, RoundingMode.UP).doubleValue();
    }
}
