package config;

import core.entities.Discount;
import core.entities.Product;
import dataProviders.out.CartPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartPortImplementorTest {

    CartPort cartPort;

    @BeforeEach
    public void init() {
        cartPort = new CartPortImplementor();
    }

    @Test
    public void cart_starts_empty(){
        assertEquals(0,cartPort.listProduct().size());
        assertEquals(0,cartPort.listDiscount().size());
    }

    @Test
    public void changes_on_product_cart_are_persisted() {
        Product product = new Product("Tomato 🍅", 52L, 15.0, 21.0);
        cartPort.updateProductCart(product);
        assertEquals(1,cartPort.listProduct().size());
    }

    @Test
    public void changes_on_discount_cart_are_persisted() {
        Discount discount = new Discount("PROMO_5",  21.0);
        cartPort.updateDiscountCart(discount);
        assertEquals(1,cartPort.listDiscount().size());
    }

}