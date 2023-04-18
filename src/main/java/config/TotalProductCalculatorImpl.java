package config;

import core.util.TotalProductCalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class TotalProductCalculatorImpl implements TotalProductCalculator {
    @Override
    public Double totalize(List<Double> pricesBeforeDiscount, List<Double> discountAmounts) {
        BigDecimal totalBeforeDiscount = pricesBeforeDiscount.stream().map(BigDecimal::new).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal totalDiscount = discountAmounts.stream().map(BigDecimal::new).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal totalWithPercentage = totalBeforeDiscount.multiply(
                BigDecimal.ONE.subtract(
                        totalDiscount.divide(BigDecimal.valueOf(100), 2,RoundingMode.HALF_EVEN)
                )
        );
        return totalWithPercentage.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }
}
