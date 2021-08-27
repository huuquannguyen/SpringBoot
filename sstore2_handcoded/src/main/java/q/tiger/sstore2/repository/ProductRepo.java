package q.tiger.sstore2.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import q.tiger.sstore2.model.Product;

@Repository
public class ProductRepo implements Dao<Product>{    
    private List<Product> list = new ArrayList<>();

    public ProductRepo() {
        intiData();
        System.out.println(list);
    }

    @Override
    public List<Product> getall() {
        return list;
    }

    @Override
    public Optional<Product> search(Product t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Product> searchById(int id) {
        return list.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public void create(Product t) {
        int index = list.size() + 1;
        t.setId(index);
        // t.setPhoto("product_" + index + ".png");
        list.add(t);
    }

    @Override
    public void remove(int id) {
        Optional<Product> delProduct = searchById(id);
        if(delProduct.isPresent()){
            list.remove(id);
        }
    }

    @Override
    public void update(Product t) {
        int id = t.getId();
        list.set(id, t);
    }

    private void intiData(){
        create(new Product("Joran 1 Travis Scott", 190, "Nike", 10, "1.jpg"));
        create(new Product("Ultra Boost 2.0", 169, "Adidas", 19, "2.jpg"));
        create(new Product("Yeezy Boost", 210, "Adidas", 2, "3.jpg"));
        create(new Product("Jordan 1 Black Mocha", 190, "Nike", 12, "4.jpg"));
    }

}
