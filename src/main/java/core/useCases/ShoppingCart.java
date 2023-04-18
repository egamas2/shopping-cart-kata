package core.useCases;

import core.entities.Discount;
import core.entities.Product;
import dataProviders.in.DiscountPort;
import dataProviders.in.ProductPort;
import dataProviders.out.*;

import java.util.Optional;

public abstract class ShoppingCart {

    private final DiscountPort discountRepo;
    private final ProductPort productRepo;

    private final DiscountPrinter discountPrinter;
    private final ProductPrinter productPrinter;

    private final CartPort cartPort;

    private final CartPrinter cartPrinter;

    public ShoppingCart(DiscountPort discountRepo, ProductPort productRepo, DiscountPrinter discountPrinter,
                        ProductPrinter productPrinter, CartPort cartPort, CartPrinter cartPrinter) {
        this.discountRepo = discountRepo;
        this.productRepo = productRepo;
        this.discountPrinter = discountPrinter;
        this.productPrinter = productPrinter;
        this.cartPort = cartPort;
        this.cartPrinter = cartPrinter;
    }

    public void addProduct(String productName) {
        Optional<Product> product = productRepo.getByName(productName);
        product.ifPresent(cartPort::updateProductCart);
    }

    public void addDiscount(String productName) {
        Optional<Discount> discount = discountRepo.getByName(productName);
        discount.ifPresent(cartPort::updateDiscountCart);
    }

    public void list() {
        cartPrinter.printCart(cartPort.listProduct(),cartPort.listDiscount());
    }
}
