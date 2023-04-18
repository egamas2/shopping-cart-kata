package core.useCases;

import config.*;
import core.util.TotalProductCalculator;
import dataProviders.in.DiscountPort;
import dataProviders.in.ProductPort;
import dataProviders.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class ShoppingCartAcceptanceTest {

    ShoppingCart cart;
    DiscountPort discountRepo = new DiscountPortImplementor();
    ProductPort productRepo = new ProductPortImplementor();
    PrintStream outStream = mock(PrintStream.class);
    OutPort outPort = new OutPortImpl(outStream);
    DiscountPrinter discountPrinter = new DiscountPrinterImpl();
    ProductPrinter productPrinter = new ProductPrinterImpl();
    CartPort cartPort = new CartPortImplementor();

    TotalsPrinter totalsPrinter = new TotalsPrinterImpl();

    TotalProductCalculator totalProductCalculator = new TotalProductCalculatorImpl();

    CartPrinter cartPrinter = new CartPrinterImpl(outPort, discountPrinter, productPrinter, totalsPrinter, totalProductCalculator);

    @BeforeEach
    public void init() {
        cart = new ShoppingCartImplementor(discountRepo, productRepo, discountPrinter, productPrinter, cartPort, cartPrinter);
    }

    @Test
    public void empty_shopping_cart_can_be_listed() {
//        given: nothing has happened and shopping cart is empty
//        when:
        cart.list();
        //then:
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(outStream, times(9)).println((CharSequence) argumentCaptor.capture());
        assertEquals(
                List.of(
                        "--------------------------------------------",
                        "| Product name | Price with VAT | Quantity |",
                        "| -----------  | -------------- | -------- |",
                        "--------------------------------------------",
                        "| Promotion:                               |",
                        "--------------------------------------------",
                        "| Total products: 0                        |",
                        "| Total price: 0.00 €                      |",
                        "--------------------------------------------"
                ), argumentCaptor.getAllValues()
        );
    }

    @Test
    public void shopping_cart_with_products_can_be_listed() {
//        given: nothing has happened and shopping cart is empty
//        when:
        cart.addProduct("Iceberg");
        cart.addProduct("Tomatoe");
        cart.addProduct("Chicken");
        cart.addProduct("Bread");
        cart.addProduct("Corn");
        cart.list();
        //then:
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(outStream, times(14)).println((CharSequence) argumentCaptor.capture());
        assertEquals(
                List.of(
                        "--------------------------------------------",
                        "| Product name | Price with VAT | Quantity |",
                        "| -----------  | -------------- | -------- |",
                        "| Iceberg 🥬   | 2.17 €         | 1        |",
                        "| Tomatoe 🍅   | 0.73 €         | 1        |",
                        "| Chicken 🍗   | 1.83 €         | 1        |",
                        "| Bread 🍞     | 0.88 €         | 1        |",
                        "| Corn 🌽      | 1.50 €         | 1        |",
                        "--------------------------------------------",
                        "| Promotion:                               |",
                        "--------------------------------------------",
                        "| Total products: 5                        |",
                        "| Total price: 7.11 €                      |",
                        "--------------------------------------------"
                ), argumentCaptor.getAllValues()
        );
    }

    @Test
    public void shopping_cart_with_repeated_products_can_be_listed() {
//        given: nothing has happened and shopping cart is empty
//        when:
        cart.addProduct("Iceberg");
        cart.addProduct("Iceberg");
        cart.addProduct("Iceberg");
        cart.addProduct("Tomatoe");
        cart.addProduct("Chicken");
        cart.addProduct("Bread");
        cart.addProduct("Bread");
        cart.addProduct("Corn");
        cart.list();
        //then:
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(outStream, times(14)).println((CharSequence) argumentCaptor.capture());
        assertEquals(
                List.of(
                        "--------------------------------------------",
                        "| Product name | Price with VAT | Quantity |",
                        "| -----------  | -------------- | -------- |",
                        "| Iceberg 🥬   | 2.17 €         | 3        |",
                        "| Tomatoe 🍅   | 0.73 €         | 1        |",
                        "| Chicken 🍗   | 1.83 €         | 1        |",
                        "| Bread 🍞     | 0.88 €         | 2        |",
                        "| Corn 🌽      | 1.50 €         | 1        |",
                        "--------------------------------------------",
                        "| Promotion:                               |",
                        "--------------------------------------------",
                        "| Total products: 8                        |",
                        "| Total price: 12.33 €                     |",
                        "--------------------------------------------"
                ), argumentCaptor.getAllValues()
        );
    }

    @Test
    public void shopping_cart_with_repeated_products_and_discount_can_be_listed() {
//        given: nothing has happened and shopping cart is empty
//        when:
        cart.addProduct("Iceberg");
        cart.addProduct("Iceberg");
        cart.addProduct("Iceberg");
        cart.addProduct("Tomatoe");
        cart.addProduct("Chicken");
        cart.addProduct("Bread");
        cart.addProduct("Bread");
        cart.addProduct("Corn");
        cart.addDiscount("PROMO_5");
        cart.list();
        //then:
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(outStream, times(14)).println((CharSequence) argumentCaptor.capture());
        assertEquals(
                List.of(
                        "--------------------------------------------",
                        "| Product name | Price with VAT | Quantity |",
                        "| -----------  | -------------- | -------- |",
                        "| Iceberg 🥬   | 2.17 €         | 3        |",
                        "| Tomatoe 🍅   | 0.73 €         | 1        |",
                        "| Chicken 🍗   | 1.83 €         | 1        |",
                        "| Bread 🍞     | 0.88 €         | 2        |",
                        "| Corn 🌽      | 1.50 €         | 1        |",
                        "--------------------------------------------",
                        "| Promotion: 5% off with code PROMO_5      |",
                        "--------------------------------------------",
                        "| Total products: 8                        |",
                        "| Total price: 11.71 €                     |",
                        "--------------------------------------------"
                ), argumentCaptor.getAllValues()
        );
    }
}