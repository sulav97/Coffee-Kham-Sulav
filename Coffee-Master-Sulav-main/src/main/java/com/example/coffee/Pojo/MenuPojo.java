package com.example.coffee.Pojo;


import com.example.coffee.Entity.Menu;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuPojo {
    private Integer id;
    @NotEmpty(message = "Name shouldn't be empty")
    private String name;

    @NotEmpty(message = "message should be at least a sentence")
    private String item_description;

    @NotEmpty(message = "quantity should not be empty")
    private String item_quantity;


    private String price;
    private String date;


    private MultipartFile image;

    public MenuPojo(Menu menu){
        this.id=menu.getId();

        this.name=menu.getName();
        this.item_description= menu.getItem_description();
        this.item_quantity=menu.getItem_quantity();
        this.price=menu.getPrice();
        this.date=menu.getDate();

    }

}

