package config;

import core.entities.OrderedCartItem;
import core.entities.Product;
import dataProviders.out.OutPort;
import dataProviders.out.ProductPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ProductPrinterImplTest {

    ProductPrinter productPrinter;
    OutPort outPort = mock(OutPort.class);
    Product product = new Product("Iceberg 🥬", 155L, 15.0, 21.0);

    @BeforeEach
    public void init() {
        productPrinter = new ProductPrinterImpl();
    }

    @Test
    public void header_is_sent_to_out_port() {
        productPrinter.outputHeader(outPort);
        verify(outPort, times(3)).print(any(CharSequence.class));
    }

    @Test
    public void data_is_sent_to_out_port() {
        productPrinter.outputData(outPort, List.of(new OrderedCartItem<>(product, 0)));
        verify(outPort).print(any(CharSequence.class));
    }

    @Test
    public void header_is_printed_as_expected() {
        productPrinter.outputHeader(outPort);
        verify(outPort).print("--------------------------------------------");
        verify(outPort).print("| Product name | Price with VAT | Quantity |");
        verify(outPort).print("| -----------  | -------------- | -------- |");
    }

    @Test
    public void data_is_printed_as_expected_for_one_product() {
        Product productWithPrice = mock(Product.class);
        when(productWithPrice.getPriceWithVAT()).thenReturn(2.17);
        when(productWithPrice.getName()).thenReturn("Iceberg 🥬");
        productPrinter.outputData(outPort, List.of(new OrderedCartItem<>(productWithPrice, 0)));
        verify(outPort, times(1)).print("| Iceberg 🥬   | 2.17 €         | 1        |");
    }

    @Test
    public void data_is_printed_as_expected_for_two_product_same_name() {
        Product productWithPrice = mock(Product.class);
        when(productWithPrice.getPriceWithVAT()).thenReturn(2.17);
        when(productWithPrice.getName()).thenReturn("Iceberg 🥬");
        productPrinter.outputData(outPort, List.of(new OrderedCartItem<>(productWithPrice, 0), new OrderedCartItem<>(productWithPrice, 1)));
        verify(outPort).print("| Iceberg 🥬   | 2.17 €         | 2        |");
    }

    @Test
    public void data_is_printed_as_expected_for_two_product_different_name() {
        Product productWithPriceIceberg = mock(Product.class);
        when(productWithPriceIceberg.getPriceWithVAT()).thenReturn(2.17);
        when(productWithPriceIceberg.getName()).thenReturn("Iceberg 🥬");

        Product productWithPriceTomatoe = mock(Product.class);
        when(productWithPriceTomatoe.getPriceWithVAT()).thenReturn(0.42);
        when(productWithPriceTomatoe.getName()).thenReturn("Tomatoe 🍅");
        productPrinter.outputData(outPort, List.of(new OrderedCartItem<>(productWithPriceIceberg,0 ), new  OrderedCartItem<>(productWithPriceTomatoe,1)));
        verify(outPort).print("| Iceberg 🥬   | 2.17 €         | 1        |");
        verify(outPort).print("| Tomatoe 🍅   | 0.42 €         | 1        |");
    }
}