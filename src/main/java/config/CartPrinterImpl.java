package config;

import core.util.TotalProductCalculator;
import dataProviders.out.*;

public class CartPrinterImpl extends CartPrinter {
    public CartPrinterImpl(OutPort outPort, DiscountPrinter discountPrinter, ProductPrinter productPrinter, TotalsPrinter totalsPrinter, TotalProductCalculator totalProductCalculator) {
        super(outPort, discountPrinter, productPrinter, totalsPrinter,totalProductCalculator);
    }
}
