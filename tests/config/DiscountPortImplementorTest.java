package config;

import dataProviders.in.DiscountPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountPortImplementorTest {

    DiscountPort discountPort;

    @BeforeEach
    public void init() {
        discountPort = new DiscountPortImplementor();
    }

    @Test
    public void get_by_name_returns_existing_element(){
        assertTrue(discountPort.getByName("PROMO_5").isPresent());
    }

    @Test
    public void get_by_name_does_not_return_non_existing_element(){
        assertTrue(discountPort.getByName("Gold").isEmpty());
    }

}