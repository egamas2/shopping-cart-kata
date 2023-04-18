package config;

import core.entities.Discount;
import core.entities.Product;
import core.util.TotalProductCalculator;
import dataProviders.out.OutPort;
import dataProviders.out.TotalsPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TotalsPrinterImplementorTest {

    TotalsPrinter totalsPrinter = new TotalsPrinterImpl();
    TotalProductCalculator totalProductCalculator;

    OutPort outPort = mock(OutPort.class);

    List<Product> products = mock(List.class);

    List<Discount> discounts = mock(List.class);

    @BeforeEach
    public void init() {
        totalProductCalculator = mock(TotalProductCalculator.class);
        when(products.stream()).thenReturn(new ArrayList<Product>().stream());
        when(discounts.stream()).thenReturn(new ArrayList<Discount>().stream());
    }

    @Test
    public void totals_are_printed_for_empty_cart() {
        when(totalProductCalculator.totalize(any(List.class), any(List.class))).thenReturn(0.0);
        when(products.size()).thenReturn(0);
        totalsPrinter.outputTotals(outPort, emptyList(), emptyList(), totalProductCalculator);
        ArgumentCaptor<String> lines = ArgumentCaptor.forClass(String.class);
        verify(outPort, times(4)).print(lines.capture());
        assertEquals(List.of(
                "--------------------------------------------",
                "| Total products: 0                        |",
                "| Total price: 0.00 €                      |",
                "--------------------------------------------"
        ), lines.getAllValues());
    }

    @Test
    public void totals_are_printed_for_non_empty_cart() {
        when(totalProductCalculator.totalize(any(List.class), any(List.class))).thenReturn(27.12);
        when(products.size()).thenReturn(2);
        totalsPrinter.outputTotals(outPort, products, discounts, totalProductCalculator);
        ArgumentCaptor<String> lines = ArgumentCaptor.forClass(String.class);
        verify(outPort, times(4)).print(lines.capture());
        assertEquals(List.of(
                "--------------------------------------------",
                "| Total products: 2                        |",
                "| Total price: 27.12 €                     |",
                "--------------------------------------------"
        ), lines.getAllValues());
    }
}