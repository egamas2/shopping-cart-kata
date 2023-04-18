package config;

import core.entities.Discount;
import dataProviders.in.DiscountPort;

import java.util.List;
import java.util.Optional;

public class DiscountPortImplementor implements DiscountPort {
    @Override
    public Optional<Discount> getByName(String name) {
        if (name != null) {
            return getAll().stream().filter(discount -> discount.getName().contains(name)).findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<Discount> getAll() {
        return List.of(
                new Discount("PROMO_5", 5.0),
                new Discount("PROMO_10", 10.0)
        );
    }
}
