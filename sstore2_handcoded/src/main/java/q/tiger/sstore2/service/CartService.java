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
import q.tiger.sstore2.model.User;
import q.tiger.sstore2.repository.ProductRepo;

@Service
public class CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserService userService;

    public void addToCart(HttpSession session, int id){
        User user = (User)session.getAttribute("userSession");
        if(user == null){
            user = new User();
        }
        HashMap<Integer, OrderLine> cart = user.getMyCart();
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
        User user = (User)session.getAttribute("userSession");
        if(user == null){
            user = new User();
        }
        HashMap<Integer, OrderLine> cart = user.getMyCart();
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
        User user = (User)session.getAttribute("userSession");
        if(user == null){
            user = new User();
        }
        HashMap<Integer, OrderLine> cart = user.getMyCart();
        Optional<Product> product = productRepo.searchById(id);
        if(product.isPresent()){
            cart.remove(id);
        }
        session.setAttribute("CART", cart);
    }

    public int getNumberOfProductInCart(HttpSession session){
        User user = (User)session.getAttribute("userSession");
        if(user == null){
            user = new User();
        }
        HashMap<Integer, OrderLine> cart = user.getMyCart();
        return cart.values().stream().mapToInt(OrderLine::getQuantity).sum();
    }

    public Cart getCart(HttpSession session){
        User user = (User)session.getAttribute("userSession");
        if(user == null){
            user = new User();
        }
        HashMap<Integer, OrderLine> cart = user.getMyCart();
        List<OrderLine> orderLines = cart.values().stream().toList();
        return new Cart(orderLines, 0.02, true);
        // HashMap<Integer,OrderLine> cart = new HashMap<>();
        // var rawCart = session.getAttribute("CART");
        // if(rawCart instanceof HashMap){
        //     cart = (HashMap<Integer, OrderLine>) rawCart;
        // List<OrderLine> orderLines = cart.values().stream().toList();
        // return new Cart(orderLines, 0.02, true);
        // }else{
        //     return new Cart();
        // }
    }
}
