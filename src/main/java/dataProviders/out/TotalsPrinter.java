package dataProviders.out;

import core.entities.Discount;
import core.entities.OrderedCartItem;
import core.entities.Product;
import core.util.TotalProductCalculator;

import java.util.List;

public abstract class TotalsPrinter {

    public abstract void outputTotals(OutPort out, List<Product> products, List<Discount> discounts, TotalProductCalculator totalizer);
}
