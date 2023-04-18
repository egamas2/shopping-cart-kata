package config;

import core.entities.Discount;
import core.entities.OrderedCartItem;
import dataProviders.out.DiscountPrinter;
import dataProviders.out.OutPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DiscountPrinterImplTest {

    DiscountPrinter discountPrinter;
    OutPort outPort = mock(OutPort.class);

    Discount DISCOUNT_5 = new Discount("PROMO_5", 5.0);
    Discount DISCOUNT_10 = new Discount("PROMO_10", 10.0);

    @BeforeEach
    public void init() {
        discountPrinter = new DiscountPrinterImpl();
    }

    @Test
    public void header_is_sent_to_out_port() {
        discountPrinter.outputHeader(outPort);
        verify(outPort).print(any(CharSequence.class));
    }

    @Test
    public void data_is_sent_to_out_port() {
        discountPrinter.outputData(outPort, of(new OrderedCartItem<>((DISCOUNT_5), 0)));
        verify(outPort).print(any(CharSequence.class));
    }

    @Test
    public void header_is_printed_as_expected() {
        discountPrinter.outputHeader(outPort);
        verify(outPort).print("--------------------------------------------");
    }

    @Test
    public void data_is_printed_as_expected_for_one_product() {
        discountPrinter.outputData(outPort, of(new OrderedCartItem<>((DISCOUNT_5), 0)));
        verify(outPort, times(1)).print("| Promotion: 5% off with code PROMO_5      |");
    }

    @Test
    public void data_is_printed_as_expected_for_two_product_same_name() {
        discountPrinter.outputData(outPort, of(new OrderedCartItem<>((DISCOUNT_5), 0), new OrderedCartItem<>((DISCOUNT_5), 1)));
        verify(outPort, times(2)).print("| Promotion: 5% off with code PROMO_5      |");
    }

    @Test
    public void data_is_printed_as_expected_for_two_product_different_name() {
        ArgumentCaptor<String> outCaptor = ArgumentCaptor.forClass(String.class);
        discountPrinter.outputData(outPort, of(new OrderedCartItem<>((DISCOUNT_5), 0), new OrderedCartItem<>((DISCOUNT_10), 1)));
        verify(outPort, times(2)).print(outCaptor.capture());
        assertEquals(List.of(
                "| Promotion: 5% off with code PROMO_5      |",
                "| Promotion: 10% off with code PROMO_10    |"
        ), outCaptor.getAllValues());

    }

}