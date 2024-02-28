package com.example.coffee.Controller;


// npm install datatables.net    # Core library
// npm install datatables.net-dt # Styling


import com.example.coffee.Entity.Gallery;
import com.example.coffee.Entity.Menu;
import com.example.coffee.Pojo.ContactPojo;
import com.example.coffee.Pojo.UserPojo;
import com.example.coffee.Services.GalleryServices;
import com.example.coffee.Services.MenuService;
import com.example.coffee.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private  final GalleryServices galleryServices;
    private  final UserService userService;
    private  final MenuService menuService;



    @GetMapping("")
    public String geHome(Model model, Principal principal,Authentication authentication) {

        if (authentication!=null){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("Admin")) {
                    return "redirect:/admin/dashboard";
                }
            }
        }

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/user/login";
        }

        //menu

        List<Menu> menus = menuService.fetchAll();
        model.addAttribute("menu", menus);

        Integer id = userService.findByEmail(principal.getName()).getId();
//        List<Cart> list = cartService.fetchAll(id);

//        double total = 0.0;
//        for(Cart totalCalc:list){
//            if (totalCalc.getProduct().getProduct_quantity()>0) total += totalCalc.getQuantity()*totalCalc.getProduct().getProduct_price();
//        }


        List<Menu> newMenus = menuService.fetchNew();
        model.addAttribute("newMenus", newMenus);

//        List<Menu> trendingProducts = menuService.trending();
//        model.addAttribute("trending", trendingProducts);

//        model.addAttribute("total", total);
//        model.addAttribute("cartItems", list);


//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return ("home");
    }


    //aboutpage


    @GetMapping("/about")
    public String getAbout(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
//        model.addAttribute("info",userService.findByEmail(principal.getName()));


        return "about_us";
    }


    //cart page


    @GetMapping("/addToCart")
    public String getAddToCart (Integer id,Model model, Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
//        model.addAttribute("update", new UserPojo());
//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "AddtoCart";
    }

    @GetMapping("/checkout")
    public String getCheckout (Integer id,Model model, Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
//        model.addAttribute("update", new UserPojo());
//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "Checkout_form";
    }


    //contactpage

    @GetMapping("/contact")
    public String getPage( Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
        model.addAttribute("contact", new ContactPojo());
//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "contactPage";}




//gallery page

    @GetMapping("/gallery")
    public String getGallery( Model model,Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
        List<Gallery> gallerys = galleryServices.fetchAll();
        model.addAttribute("imagelist", gallerys.stream().map(gallery ->
                Gallery.builder()
                        .id(gallery.getId())
                        .name(gallery.getName())
                        .imageBase64(getImageBase64(gallery.getImage()))
                        .build()

        ));
//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "gallery";
    }




    //profile page

    @GetMapping("/profile")
    public String getUserProfile (Integer id,Model model, Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
        model.addAttribute("update", new UserPojo());
        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "profile";
    }


    @PostMapping("/updateUser")
    public String update(@Valid UserPojo userpojo) {
        userService.save(userpojo);
        return "redirect:/home/profile";
    }





    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/Gallery/";
        File file = new File(filePath + fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }

    //menu

    @GetMapping("/menu")
    public String getMenu(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        List<Menu> menus = menuService.fetchAll();
        model.addAttribute("menu", menus);

        return "menu";
    }

}
