package core.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = {"name"})
@Getter
public final class Discount implements CartItem<Discount>{
    private final String name;
    private final Double amount;

    public Discount(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }
}
