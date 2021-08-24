package q.tiger.sstore2.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Address {
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Zip is required")
    private String zip;
    @NotBlank(message = "State is required")
    private String state;
}
