package dataProviders.out;

import core.entities.Discount;
import core.entities.OrderedCartItem;
import core.entities.Product;
import core.util.TotalProductCalculator;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CartPrinter {
    DiscountPrinter discountPrinter;
    ProductPrinter productPrinter;
    TotalsPrinter totalsPrinter;
    TotalProductCalculator totalProductCalculator;
    OutPort outPort;

    protected CartPrinter(OutPort outPort, DiscountPrinter discountPrinter, ProductPrinter productPrinter, TotalsPrinter totalsPrinter, TotalProductCalculator totalProductCalculator) {
        this.discountPrinter = discountPrinter;
        this.productPrinter = productPrinter;
        this.totalsPrinter = totalsPrinter;
        this.outPort = outPort;
        this.totalProductCalculator = totalProductCalculator;
    }

    public void printCart(List<OrderedCartItem<Product>> products, List<OrderedCartItem<Discount>> discounts) {
        productPrinter.outputHeader(this.outPort);
        productPrinter.outputData(this.outPort, products);
        discountPrinter.outputHeader(this.outPort);
        discountPrinter.outputData(this.outPort, discounts);
        List<Product> productItems = products.stream().map(OrderedCartItem::getItem).collect(Collectors.toList());
        List<Discount> discountItems = discounts.stream().map(OrderedCartItem::getItem).collect(Collectors.toList());
        totalsPrinter.outputTotals(this.outPort, productItems, discountItems, totalProductCalculator);
    }
}
