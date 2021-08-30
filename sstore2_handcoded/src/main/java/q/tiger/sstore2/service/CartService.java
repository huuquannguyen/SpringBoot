package q.tiger.sstore2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import q.tiger.sstore2.model.Cart;
import q.tiger.sstore2.model.OrderLine;
import q.tiger.sstore2.model.Product;
import q.tiger.sstore2.repository.ProductRepo;

@Service
public class CartService {

    @Autowired
    ProductRepo productRepo;

    public void addToCart(HttpSession session, int id){
        HashMap<Integer, OrderLine> cart = new HashMap<>();
        var rawCart = session.getAttribute("CART");
        if(rawCart instanceof HashMap){
            cart = (HashMap<Integer, OrderLine>) rawCart;
        }
        Optional<Product> product = productRepo.searchById(id);
        if(product.isPresent()){
            OrderLine orderLine = cart.get(id);
            if(orderLine != null){
                orderLine.increaseByOne();
                cart.put(id, orderLine);
            }else{
                cart.put(id, new OrderLine(product.get(), 1));
            }
        }
        session.setAttribute("CART", cart);
    }

    public void decreaseProductQuantity(HttpSession session, int id){
        HashMap<Integer, OrderLine> cart = new HashMap<>();
        var rawCart = session.getAttribute("CART");
        if(rawCart instanceof HashMap){
            cart = (HashMap<Integer, OrderLine>) rawCart;
        }
        Optional<Product> product = productRepo.searchById(id);
        if(product.isPresent()){
            OrderLine orderLine = cart.get(id);
            if(orderLine != null){
                if(orderLine.getQuantity() == 1){
                    deleteOrderLine(id, session);
                }else{
                    orderLine.decreaseByOne();
                    cart.put(id, orderLine);
                }
            }
            session.setAttribute("CART", cart);
        }
    }

    public void deleteOrderLine(int id, HttpSession session){
        HashMap<Integer, OrderLine> cart = new HashMap<>();
        var rawCart = session.getAttribute("CART");
        if(rawCart instanceof HashMap){
            cart = (HashMap<Integer, OrderLine>) rawCart;
        }
        Optional<Product> product = productRepo.searchById(id);
        if(product.isPresent()){
            cart.remove(id);
        }
        session.setAttribute("CART", cart);
    }

    public int getNumberOfProductInCart(HttpSession session){
        HashMap<Integer, OrderLine> cart = new HashMap<>();
        var rawCart = session.getAttribute("CART");
        if(rawCart instanceof HashMap){
            cart = (HashMap<Integer, OrderLine>) rawCart;
            return cart.values().stream().mapToInt(OrderLine::getQuantity).sum();
        }else{
            return 0;
        }
    }

    public Cart getCart(HttpSession session){
        HashMap<Integer,OrderLine> cart = new HashMap<>();
        var rawCart = session.getAttribute("CART");
        if(rawCart instanceof HashMap){
            cart = (HashMap<Integer, OrderLine>) rawCart;
            List<OrderLine> orderLines = cart.values().stream().toList();
            return new Cart(orderLines, 0.02, true);
        }else{
            return new Cart();
        }
    }
}
