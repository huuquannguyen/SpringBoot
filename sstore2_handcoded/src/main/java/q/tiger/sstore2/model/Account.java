package q.tiger.sstore2.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @NotBlank(message = "Username is required")
    private String username;
    @Size(min=8, message = "Password must be equal or more than 8 characters")
    private String password;

    public boolean equals(Account a){
        return (this.username.equals(a.getUsername()) && this.password.equals(a.getPassword()));
    }
}


