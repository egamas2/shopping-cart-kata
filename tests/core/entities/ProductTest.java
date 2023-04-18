package core.entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    Map<Product, Double> cases;

    @BeforeEach
    public void init() {
        cases = Map.of(
                new Product("Iceberg 🥬", 155L, 15.0, 21.0), 2.17,
                new Product("Tomato 🍅", 52L, 15.0, 21.0), 0.73,
                new Product("Chicken 🍗", 134L, 12.0, 21.0), 1.83,
                new Product("Bread 🍞", 71L, 12.0, 10.0), 0.88,
                new Product("Corn 🌽", 121L, 12.0, 10.0), 1.50
        );
    }

    @Test
    public void product_calculates_total_price_with_revenue_and_VAT() {
        for (Map.Entry<Product, Double> entry : cases.entrySet()) {
            System.out.println("Product " + entry.getKey().getName());
            assertEquals(entry.getValue(), entry.getKey().getPriceWithVAT());
        }
    }
}