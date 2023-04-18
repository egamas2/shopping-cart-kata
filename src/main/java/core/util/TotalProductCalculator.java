package core.util;

import java.util.List;

public interface TotalProductCalculator {
    Double totalize(List<Double> pricesBeforeDiscount, List<Double> discountAmounts);
}
