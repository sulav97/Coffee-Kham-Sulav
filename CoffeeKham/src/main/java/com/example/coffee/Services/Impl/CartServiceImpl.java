package com.example.coffee.Services.Impl;

import com.example.coffee.Entity.Cart;
import com.example.coffee.Entity.Menu;
import com.example.coffee.Pojo.CartPojo;
import com.example.coffee.Repo.CartRepo;
import com.example.coffee.Repo.MenuRepo;
import com.example.coffee.Repo.UserRepo;
import com.example.coffee.Services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

//import static com.system.impl.services.coffee_shop.ProductServiceImpl.getImageBase64;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final MenuRepo menuRepo;
//    private final SaleRepo saleRepo;

    @Override
    public String saveToCart(Integer id, Principal principal) {
        Cart cart = new Cart();
        cart.setUser(userRepo.findByEmail(principal.getName()).orElseThrow());
        cart.setMenu(menuRepo.findById(id).orElseThrow());
        cart.setQuantity(1);
        cartRepo.save(cart);

        return "Saved";
    }
    @Override
    public String deleteFromCart(Integer id) {
        cartRepo.deleteById(id);
        return "Deleted";
    }

    @Override
    public String updateQuantity(Cart cart) {
        cartRepo.save(cart);
        return "Updated";
    }

    @Override
    public List<Cart> fetchAll(Integer id) {
        List<Cart> allItems = cartRepo.fetchAll(id).orElseThrow();
        for (Cart cart : allItems){
            cart.setMenu(Menu.builder()
                    .id(cart.getMenu().getId())
                    .item_quantity(cart.getMenu().getItem_quantity())
                    .name(cart.getMenu().getName())
                    .build());
        }
        return allItems;
    }

    @Override
    public List<Cart> fetchAvailable(Integer id) {
        return null;
    }


    @Override
    public Cart fetchOne(Integer id) {
        return cartRepo.findById(id).orElseThrow();
    }

    @Override
    public String checkout(Integer id, CartPojo pojo, List<Cart> itemsToPurchase) {
        for (Cart value : itemsToPurchase) {
            Cart cart = new Cart();
            cart.setId(value.getId());
            cart.setUser(value.getUser());
            cart.setMenu(value.getMenu());
            cart.setQuantity(value.getQuantity());

            cartRepo.save(cart);
        }
        return "Saved Purchase";
    }

    @Override
    public String updateProduct(double quantity, Integer id) {
       menuRepo.updateQuantity(quantity, id);
        return "Updated Quantity";
    }
}