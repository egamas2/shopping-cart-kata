package core.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@EqualsAndHashCode(of = {"item","order"})
@Getter
public class OrderedCartItem<T> implements Comparable<OrderedCartItem<T>>{
    final T item;
    final Integer order;

    public OrderedCartItem(T item, Integer order) {
        this.item = item;
        this.order = order;
    }

    @Override
    public int compareTo(OrderedCartItem<T> o) {
        if (Objects.isNull(o)) {
            return 1;
        }
        return this.order.compareTo(o.getOrder());
    }
}
