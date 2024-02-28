package com.example.coffee.Pojo;

import com.example.coffee.Entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {
    private Integer id;
    @NotEmpty(message = "email should not be empty")
    private String email;
    @NotEmpty(message = "Phone can't be empty")
    private String mobile_no;
    @NotEmpty(message = "Full name can't be empty")
    private String fullname;

    private String password;





    public UserPojo(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.mobile_no = user.getMobileNo();
        this.fullname = user.getFullname();
        this.password = user.getPassword();
    }


}
