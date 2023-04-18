package dataProviders.in;

import core.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductPort {
    Optional<Product> getByName(String name);
    List<Product> getAll();
}

