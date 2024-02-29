package com.example.coffee.Pojo;

import com.example.coffee.Entity.Contact;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ContactPojo {

    private Integer id;
    @NotEmpty(message = "fullname should not be empty")
    private  String fullname;
    @NotEmpty(message = "email should not be empty")
    private String email;

    private  String subject;
    @NotEmpty(message = "message should be at least a sentence")
    private  String message;

    public ContactPojo(Contact contact) {
        this.id=contact.getId();
        this.fullname=contact.getFullname();
        this.email=contact.getEmail();
        this.subject=contact.getSubject();
        this.message=contact.getMessage();
    }
}

