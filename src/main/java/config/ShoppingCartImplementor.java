package config;

import core.useCases.ShoppingCart;
import dataProviders.in.DiscountPort;
import dataProviders.in.ProductPort;
import dataProviders.out.CartPort;
import dataProviders.out.CartPrinter;
import dataProviders.out.DiscountPrinter;
import dataProviders.out.ProductPrinter;

public class ShoppingCartImplementor extends ShoppingCart {


    public ShoppingCartImplementor(DiscountPort discountRepo, ProductPort productRepo, DiscountPrinter discountPrinter, ProductPrinter productPrinter, CartPort cartPort, CartPrinter cartPrinter) {
        super(discountRepo, productRepo, discountPrinter, productPrinter, cartPort, cartPrinter);
    }
}
