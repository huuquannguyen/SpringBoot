package q.tiger.sstore2.model;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class Cart {

    private List<OrderLine> orderLines;
    private double rawTotal;
    private double discount;
    private double tax;
    private double total;

    public Cart(){
        this.orderLines = Collections.emptyList();
        this.rawTotal = 0;
        this.discount = 0;
        this.tax = 0;
        this.total = 0;
    }

    public Cart(List<OrderLine> orderLines, double discountPercent, boolean isTaxIncluded){
        this.orderLines = orderLines;
        this.rawTotal = 0;

        orderLines.stream()
        .forEach(orderLine -> {
            rawTotal += orderLine.getQuantity()*orderLine.getProduct().getPrice();
        });

        discount = (double) Math.round(rawTotal*discountPercent);
        if(isTaxIncluded){
            tax = (double) Math.round((rawTotal - discount)*0.01);
        }else{
            tax = 0;
        }
        total = rawTotal - discount + tax;
    }
}
