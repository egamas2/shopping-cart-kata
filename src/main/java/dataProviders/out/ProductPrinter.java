package dataProviders.out;

import core.entities.OrderedCartItem;
import core.entities.Product;

import java.util.List;

public interface ProductPrinter {

    void outputHeader(OutPort out);

    public void outputData(OutPort out, List<OrderedCartItem<Product>> products);
}
