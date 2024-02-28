package com.example.coffee.Pojo;

import com.example.coffee.Entity.Cart;
import com.example.coffee.Entity.Menu;
import com.example.coffee.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class CartPojo {
        private Integer id;
        private Menu menu;
        private User user;
        private Integer quantity;

        public CartPojo(Cart cart) {
            this.id = cart.getId();
            this.menu = cart.getMenu();
            this.user = cart.getUser();
           this.quantity=cart.getQuantity();
        }

    }