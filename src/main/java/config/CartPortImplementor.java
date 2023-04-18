package config;

import core.entities.Discount;
import core.entities.OrderedCartItem;
import core.entities.Product;
import dataProviders.out.CartPort;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CartPortImplementor implements CartPort {

    List<Product> products = new ArrayList<>();
    List<Discount> discounts = new ArrayList<>();

    @Override
    public void updateDiscountCart(Discount discount) {
        discounts.add(discount);
    }

    @Override
    public void updateProductCart(Product product) {
        products.add(product);
    }

    @Override
    public List<OrderedCartItem<Discount>> listDiscount() {
        AtomicInteger count = new AtomicInteger();
        return Optional.of(discounts).orElseGet(Collections::emptyList).stream()
                .map(d -> new OrderedCartItem<>(d, count.getAndIncrement()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderedCartItem<Product>> listProduct() {
        AtomicInteger count = new AtomicInteger();
        return Optional.of(products).orElseGet(Collections::emptyList).stream()
                .map(d -> new OrderedCartItem<>(d, count.getAndIncrement()))
                .collect(Collectors.toList());
    }
}
