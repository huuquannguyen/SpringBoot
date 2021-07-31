package vn.techmaster.shopingcart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Info {
    private String name;
    private Long phoneNumber;
    private String email;
}
