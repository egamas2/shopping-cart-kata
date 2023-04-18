package config;

import core.entities.OrderedCartItem;
import core.entities.Product;
import dataProviders.out.OutPort;
import dataProviders.out.ProductPrinter;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductPrinterImpl implements ProductPrinter {
    private final String DATA_TEMPLATE = "| %s | %s | %s |";
    private final String PRICE_TEMPLATE = "%s €";
    private final int NAME_PAD = 12;
    private final int PRICE_PAD = 14;
    private final int QUANTITY_PAD = 8;

    private DecimalFormat priceNumberFormatter = new DecimalFormat("0.00");

    @Override
    public void outputHeader(OutPort out) {
        out.print("--------------------------------------------");
        out.print("| Product name | Price with VAT | Quantity |");
        out.print("| -----------  | -------------- | -------- |");
    }

    @Override
    public void outputData(OutPort out, List<OrderedCartItem<Product>> orderedCartItems) {
        List<Product> products = orderedCartItems.stream().sorted().map(OrderedCartItem::getItem).collect(Collectors.toList());
        Map<String, List<Product>> byName = groupByNameAndPrice(products);
        products.forEach(
                product -> {
                    List<Product> allProductsForName = byName.get(product.getName());
                    if (allProductsForName != null) {
                        String name = product.getName();
                        int quantity = allProductsForName.size();
                        Double price = 0.0;
                        Optional<Product> firstProduct = allProductsForName.stream().findFirst();
                        if (firstProduct.isPresent()) {
                            price = firstProduct.get().getPriceWithVAT();
                        }
                        out.print(formatLine(name, price, quantity));
                        byName.remove(product.getName());
                    }
                }
        );
    }

    private CharSequence formatLine(String name, Double price, Integer quantity) {
        String priceTemplateFilled = String.format(PRICE_TEMPLATE, priceNumberFormatter.format(price));
        return String.format(DATA_TEMPLATE,
                StringUtils.rightPad(name, NAME_PAD),
                StringUtils.rightPad(priceTemplateFilled, PRICE_PAD),
                StringUtils.rightPad(quantity.toString(), QUANTITY_PAD));
    }

    private static Map<String, List<Product>> groupByNameAndPrice(List<Product> products) {
        return products.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Product::getName));
    }
}
