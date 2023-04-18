package dataProviders.out;

import core.entities.Discount;
import core.entities.OrderedCartItem;
import core.entities.Product;

import java.util.List;

public interface CartPort {
    void updateDiscountCart(Discount discount);

    void updateProductCart(Product product);

    List<OrderedCartItem<Discount>> listDiscount();

    List<OrderedCartItem<Product>> listProduct();
}
