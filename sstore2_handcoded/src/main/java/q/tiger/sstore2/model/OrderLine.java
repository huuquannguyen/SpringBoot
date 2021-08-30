package q.tiger.sstore2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderLine {

    private Product product;
    private int quantity;

    public void increaseByOne(){
        this.quantity += 1;
    }

    public void decreaseByOne(){
        if(this.quantity > 1){
            this.quantity -= 1;
        }
    }
}
