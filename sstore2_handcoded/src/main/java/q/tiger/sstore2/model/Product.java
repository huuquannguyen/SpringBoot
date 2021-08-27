package q.tiger.sstore2.model;


import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private double price;
    private String brand;
    private int quantity;
    private String photo;

    public Product(String name, double price, String brand, int quantity, String photo) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.quantity = quantity;
        this.photo = photo;
    }

    
}
