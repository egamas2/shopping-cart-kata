package config;

import core.util.TotalProductCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalProductCalculatorImplTest {

    TotalProductCalculator totalProductCalculator = new TotalProductCalculatorImpl();

    @BeforeEach
    public void init() {
    }

    @Test
    public void when_summing_up_empty_products_and_empty_discounts_then_returns_zero() {
        double total = totalProductCalculator.totalize(emptyList(), emptyList());
        assertEquals(Double.valueOf(0.0), Double.valueOf(total));
    }

    @Test
    public void when_summing_three_decimal_numbers_round_up_as_expected() {
        List<Double> prices = List.of(1.999, 2.999, 0.999);
        double total = totalProductCalculator.totalize(prices, emptyList());
        assertEquals(Double.valueOf(6.0), Double.valueOf(total));
    }

    @Test
    public void when_summing_up_products_and_empty_discounts_then_returns_expected_value() {
        List<Double> prices = List.of(2.17, 0.73, 1.83, 0.88, 1.50);
        double total = totalProductCalculator.totalize(prices, emptyList());
        assertEquals(Double.valueOf(7.11), Double.valueOf(total));
    }

    @Test
    public void when_summing_up_products_and_not_empty_discounts_then_returns_expected_value() {
        List<Double> prices = List.of(2.17, 2.17, 2.17, 0.73, 1.83, 0.88, 0.88, 1.50);
        List<Double> discounts = List.of(5.0);
        double total = totalProductCalculator.totalize(prices, discounts);
        assertEquals(Double.valueOf(11.71), Double.valueOf(total));
    }

    @Test
    public void when_summing_up_products_and_two_discounts_then_returns_expected_value() {
        List<Double> prices = List.of(2.17, 2.17, 2.17, 0.73, 1.83, 0.88, 0.88, 1.50);
        List<Double> discounts = List.of(3.0, 2.0);
        double total = totalProductCalculator.totalize(prices, discounts);
        assertEquals(Double.valueOf(11.71), Double.valueOf(total));
    }

    @Test
    public void when_summing_up_products_and_one_discount_three_decimal_then_returns_expected_value() {
        List<Double> prices = List.of(2.17, 2.17, 2.17, 0.73, 1.83, 0.88, 0.88, 1.50);
        List<Double> discounts = List.of(5.009);
        double total = totalProductCalculator.totalize(prices, discounts);
        assertEquals(Double.valueOf(11.71), Double.valueOf(total));
    }

    @Test
    public void when_summing_up_products_and_two_discounts_more_decimals_then_returns_expected_value() {
        List<Double> prices = List.of(2.17, 2.17, 2.17, 0.73, 1.83, 0.88, 0.88, 1.50);
        List<Double> discounts = List.of(3.0099, 2.0099);
        double total = totalProductCalculator.totalize(prices, discounts);
        assertEquals(Double.valueOf(11.71), Double.valueOf(total));
    }

    @Test
    public void when_summing_up_products_and_two_discounts_two_decimals_then_returns_expected_value() {
        List<Double> prices = List.of(2.17, 2.17, 2.17, 0.73, 1.83, 0.88, 0.88, 1.50);
        List<Double> discounts = List.of(2.99, 1.99);
        double total = totalProductCalculator.totalize(prices, discounts);
        assertEquals(Double.valueOf(11.71), Double.valueOf(total));
    }

    @Test
    public void when_summing_up_products_and_two_discounts_three_decimals_then_returns_expected_value() {
        List<Double> prices = List.of(2.17, 2.17, 2.17, 0.73, 1.83, 0.88, 0.88, 1.50);
        List<Double> discounts = List.of(2.999, 1.999);
        double total = totalProductCalculator.totalize(prices, discounts);
        assertEquals(Double.valueOf(11.71), Double.valueOf(total));
    }
}
