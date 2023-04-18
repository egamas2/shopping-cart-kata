package dataProviders.out;

import core.entities.Discount;
import core.entities.OrderedCartItem;

import java.io.FilterOutputStream;
import java.io.PrintStream;
import java.util.List;

public interface DiscountPrinter {

     void outputHeader(OutPort out) ;

     void outputData(OutPort out, List<OrderedCartItem<Discount>> discounts) ;
}
