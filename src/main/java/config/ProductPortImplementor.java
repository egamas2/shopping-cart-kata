package config;

import core.entities.Product;
import dataProviders.in.ProductPort;

import java.util.List;
import java.util.Optional;

public class ProductPortImplementor implements ProductPort {

    @Override
    public Optional<Product> getByName(String name) {
        if (name != null) {
            return getAll().stream().filter(product -> product.getName().contains(name)).findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        return List.of(
                new Product("Iceberg 🥬", 155L, 15.0, 21.0),
                new Product("Tomatoe 🍅", 52L, 15.0, 21.0),
                new Product("Chicken 🍗", 134L, 12.0, 21.0),
                new Product("Bread 🍞", 71L, 12.0, 10.0),
                new Product("Corn 🌽", 121L, 12.0, 10.0)
        );
    }
}
