package config;

import dataProviders.in.ProductPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductPortImplementorTest {

    ProductPort productPort;

    @BeforeEach
    public void init() {
        productPort = new ProductPortImplementor();
    }

    @Test
    public void get_by_name_returns_existing_element(){
        assertTrue(productPort.getByName("Iceberg").isPresent());
    }
    @Test
    public void get_by_name_does_not_return_non_existing_element(){
        assertTrue(productPort.getByName("Gold").isEmpty());
    }
}