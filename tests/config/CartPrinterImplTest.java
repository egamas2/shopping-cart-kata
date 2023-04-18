package config;

import core.entities.Discount;
import core.entities.OrderedCartItem;
import core.entities.Product;
import core.util.TotalProductCalculator;
import dataProviders.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class CartPrinterImplTest {
    CartPrinter cartPrinter;
    OutPort outPort;
    DiscountPrinter discountPrinter;
    ProductPrinter productPrinter;

    TotalsPrinter totalsPrinter;

    TotalProductCalculator totalProductCalculator;

    List<OrderedCartItem<Product>> products = List.of(new OrderedCartItem<>(new Product("Tomato 🍅", 52L, 15.0, 21.0), 0));
    List<OrderedCartItem<Discount>> discounts = List.of(new OrderedCartItem<>(new Discount("DISCOUNT_5", 5.0), 0));

    @BeforeEach
    public void init() {
        discountPrinter = mock(DiscountPrinter.class);
        productPrinter = mock(ProductPrinter.class);
        totalsPrinter = mock(TotalsPrinter.class);
        totalProductCalculator = mock(TotalProductCalculator.class);
        cartPrinter = new CartPrinterImpl(outPort, discountPrinter, productPrinter, totalsPrinter, totalProductCalculator);
    }

    @Test
    public void both_printers_are_used() {
        //when
        cartPrinter.printCart(products, discounts);
        //then
        verifyProductPrinter();
        verifyDiscountPrinter();
        verifyTotalsPrinter();
    }

    private void verifyProductPrinter() {
        verify(productPrinter, atLeast(1)).outputHeader(any(OutPort.class));
        verify(productPrinter, atLeast(1)).outputData(any(OutPort.class), any(List.class));
    }

    private void verifyDiscountPrinter() {
        verify(discountPrinter, atLeast(1)).outputHeader(any(OutPort.class));
        verify(discountPrinter, atLeast(1)).outputData(any(OutPort.class), any(List.class));
    }

    private void verifyTotalsPrinter() {
        verify(totalsPrinter, atLeast(1)).outputTotals(any(OutPort.class), anyObject(), anyObject(), anyObject());
    }
}