package config;

import core.util.TotalProductCalculator;
import core.entities.Discount;
import core.entities.Product;
import dataProviders.out.OutPort;
import dataProviders.out.TotalsPrinter;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class TotalsPrinterImpl extends TotalsPrinter {
    private final String SEPARATOR = "--------------------------------------------";
    private final String TOTAL_PRODUCTS_TEMPLATE = "| Total products: %s |";
    private int PRODUCTS_TEMPLATE_PAD = 24;
    private final String TOTAL_PRICE_TEMPLATE = "| Total price: %s |";
    private final String PRICE_TEMPLATE = "%s €";
    private int PRICE_TEMPLATE_PAD = 27;
    private DecimalFormat priceNumberFormatter = new DecimalFormat("0.00");

    @Override
    public void outputTotals(OutPort out, List<Product> products, List<Discount> discounts, TotalProductCalculator totalizer) {

        out.print(SEPARATOR);

        List<Double> pricesBeforeDiscount = products.stream().map(Product::getPriceWithVAT).collect(Collectors.toList());
        List<Double> discountAmounts = discounts.stream().map(Discount::getAmount).collect(Collectors.toList());

        printTotalProducts(out, products);

        printTotalPrice(out, totalizer, pricesBeforeDiscount, discountAmounts);

        out.print(SEPARATOR);
    }

    private void printTotalPrice(OutPort out, TotalProductCalculator totalizer, List<Double> pricesBeforeDiscount, List<Double> discountAmounts) {
        Double total = totalizer.totalize(pricesBeforeDiscount, discountAmounts);
        String formattedTotal = StringUtils.rightPad(String.format(PRICE_TEMPLATE, priceNumberFormatter.format(total)), PRICE_TEMPLATE_PAD);
        out.print(String.format(TOTAL_PRICE_TEMPLATE, formattedTotal));
    }

    private void printTotalProducts(OutPort out, List<Product> products) {
        Integer numProducts = products.size();
        String formattedNumProducts = StringUtils.rightPad(numProducts.toString(), PRODUCTS_TEMPLATE_PAD);
        out.print(String.format(TOTAL_PRODUCTS_TEMPLATE, formattedNumProducts));
    }
}
