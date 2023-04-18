package dataProviders.in;

import core.entities.Discount;
import dataProviders.out.OutPort;

import java.util.List;
import java.util.Optional;

public interface DiscountPort {

    Optional<Discount> getByName(String name);
    List<Discount> getAll();

}
