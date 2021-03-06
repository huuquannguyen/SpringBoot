package q.tiger.sstore2.model;

import java.util.HashMap;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class User {
    
    private int id;

    @Size(min = 2, max = 30, message = "must be 2-30 characters")
    private String name;

    @Email(message = "Must be an email")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, max = 10, message = "Must be between 8-10 digits")
    private String phone;

    @Valid

    private Address address;

    @Valid
    private Account account;

    private MultipartFile photo;

    private HashMap<Integer, OrderLine> myCart;

    public User(){
        this.myCart = new HashMap<>();
    }

}
