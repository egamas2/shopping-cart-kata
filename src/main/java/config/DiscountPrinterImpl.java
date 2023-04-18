package config;

import core.entities.Discount;
import core.entities.OrderedCartItem;
import dataProviders.out.DiscountPrinter;
import dataProviders.out.OutPort;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

public class DiscountPrinterImpl implements DiscountPrinter {

    public static final String PROMO_TEMPLATE = "| Promotion: %s |";
    public static final String EMPTY_TEMPLATE = "| Promotion:                               |";
    public static final int TEMPLATE_PAD = 29;
    public static final String PROMO_DESCRIPTION_TEMPLATE = "%s%% off with code %s";

    private final DecimalFormat doubleFormatter = new DecimalFormat("0.#");

    @Override
    public void outputHeader(OutPort out) {
        out.print("--------------------------------------------");
    }

    @Override
    public void outputData(OutPort out, List<OrderedCartItem<Discount>> discounts) {
        if (discounts != null && !discounts.isEmpty()) {
            for (OrderedCartItem<Discount> discount : discounts) {
                out.print(formatOuterTemplate(discount.getItem()));
            }
        } else {
            out.print(EMPTY_TEMPLATE);
        }
    }

    private String formatOuterTemplate(Discount discount) {
        return String.format(PROMO_TEMPLATE,
                formatDescription(discount)
        );
    }

    private String formatDescription(Discount discount) {
        return StringUtils.rightPad(
                String.format(PROMO_DESCRIPTION_TEMPLATE, doubleFormatter.format(discount.getAmount()), discount.getName()), TEMPLATE_PAD);
    }
}
