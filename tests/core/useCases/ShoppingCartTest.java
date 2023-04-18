package core.useCases;

import config.CartPortImplementor;
import config.ShoppingCartImplementor;
import core.entities.Discount;
import core.entities.Product;
import dataProviders.in.DiscountPort;
import dataProviders.in.ProductPort;
import dataProviders.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShoppingCartTest {
    public static final String ICEBERG = "Iceberg 🥬";
    public static final String PROMO_5 = "PROMO_5";
    ShoppingCart cart;
    DiscountPort discountRepo = mock(DiscountPort.class);
    ProductPort productRepo = mock(ProductPort.class);
    DiscountPrinter discountPrinter = mock(DiscountPrinter.class);
    ProductPrinter productPrinter = mock(ProductPrinter.class);

    CartPort cartPort = new CartPortImplementor();

    CartPrinter cartPrinter = mock(CartPrinter.class);

    @BeforeEach
    public void init() {
        cart = new ShoppingCartImplementor(discountRepo, productRepo, discountPrinter, productPrinter, cartPort, cartPrinter);
        Product fakeProduct = new Product(ICEBERG, 155L, 15.0, 21.0);
        Discount fakeDiscount = new Discount(PROMO_5, 5.0);
        when(productRepo.getByName(Mockito.any())).thenReturn(Optional.empty());
        when(productRepo.getByName(ICEBERG)).thenReturn(Optional.of(fakeProduct));
        when(discountRepo.getByName(Mockito.any())).thenReturn(Optional.empty());
        when(discountRepo.getByName(PROMO_5)).thenReturn(Optional.of(fakeDiscount));
    }

    @Test
    public void add_existing_product_uses_data_providers() {
        //given
        //when
        cart.addProduct(ICEBERG);
        //then
        verify(productRepo, atMost(1)).getByName(ICEBERG);

        //and
        assertEquals(1, cartPort.listProduct().size());
    }

    @Test
    public void add_non_existing_product_uses_data_providers() {
        //given
        String name = "nonexistent";
        //when
        cart.addProduct(name);
        //then
        verify(productRepo, atMost(1)).getByName(name);
        assertEquals(0, cartPort.listProduct().size());
    }

    @Test
    public void add_existing_discount_uses_data_providers() {
        //when
        cart.addDiscount(PROMO_5);
        //then
        verify(discountRepo, atMost(1)).getByName(PROMO_5);
        assertEquals(1, cartPort.listDiscount().size());
    }

    @Test
    public void add_non_existing_discount_uses_data_providers() {
        //given
        String name = "nonexistent";
        //when
        cart.addDiscount(name);
        //then
        verify(discountRepo, atMost(1)).getByName(name);
        assertEquals(0, cartPort.listDiscount().size());
    }

    @Test
    public void list_empty_cart_uses_out_data_providers() {
        cart.list();
        //then
        verify(cartPrinter).printCart(anyList(), anyList());

    }

    @Test
    public void list_non_empty_cart_uses_out_data_providers() {
        cart.addProduct("Some product");
        cart.list();
        //then
        verify(cartPrinter).printCart(anyList(), anyList());
    }
}