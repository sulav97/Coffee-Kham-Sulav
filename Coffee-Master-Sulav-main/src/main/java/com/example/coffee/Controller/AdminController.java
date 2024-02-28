package com.example.coffee.Controller;


import com.example.coffee.Entity.Contact;
import com.example.coffee.Entity.Gallery;
import com.example.coffee.Entity.Menu;
import com.example.coffee.Entity.User;
import com.example.coffee.Pojo.GalleryPojo;
import com.example.coffee.Pojo.MenuPojo;
import com.example.coffee.Services.GalleryServices;
import com.example.coffee.Services.MenuService;
import com.example.coffee.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final MenuService menuService;


    private final GalleryServices galleryServices;

    //gallery

    @GetMapping("/addImage")
    public String getGallery(Model model) {
        model.addAttribute("gallery", new GalleryPojo());
        return "Admin/add_images";
    }


    @PostMapping("/addgallery")
    public String createUser(@Valid GalleryPojo galleryPojo,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        Map<String, String> requestError = validateRequest(bindingResult);
        if (requestError != null) {
            redirectAttributes.addFlashAttribute("requestError", requestError);
            return "redirect:/admin/addImage";
        }

        galleryServices.save(galleryPojo);
        redirectAttributes.addFlashAttribute("successMsg", "Image saved successfully");

        return "redirect:/admin/viewImage";
    }

    @GetMapping("/viewImage")
    public String addGallery(Model model) {
        List<Gallery> gallerys = galleryServices.fetchAll();
        model.addAttribute("imagelist", gallerys.stream().map(gallery ->
                Gallery.builder()
                        .id(gallery.getId())
                        .name(gallery.getName())
                        .imageBase64(getImageBase64(gallery.getImage()))
                        .build()

        ));
        return "Admin/viewImages";
    }
    @GetMapping("/deleteImg/{id}")
    public String delImg(@PathVariable("id") Integer id) {
        galleryServices.deleteById(id);
        return "redirect:/admin/viewImage";
    }


    public Map<String, String> validateRequest(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;

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

    //dashboard

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {


        return "Admin/dashboard";
    }

    //Menu

    @GetMapping("/addMenu")
    public String getaddMenu(Model model) {
        model.addAttribute("menu", new MenuPojo());
        return "Admin/add_menu";
    }

    @PostMapping("/saveMenu")
    public String saveMenu(@Valid MenuPojo menuPojo,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        Map<String, String> requestError = validateRequest(bindingResult);
        if (requestError != null) {
            redirectAttributes.addFlashAttribute("requestError", requestError);
            return "redirect:/admin/addMenu";
        }

        menuService.saveMenu(menuPojo);
        redirectAttributes.addFlashAttribute("successMsg", "Image saved successfully");

        return "redirect:/admin/menuDetails";
    }

    @GetMapping("/deleteMenu/{id}")
    public String delMenu(@PathVariable("id") Integer id) {
        menuService.deleteById(id);
        return "redirect:/admin/menuDetails";
    }

    @GetMapping("/menuDetails")
    public String viewMenu(Model model) {
        List<Menu> menus = menuService.fetchAll();
        model.addAttribute("menulist", menus.stream().map(menu ->
                Menu.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .item_description(menu.getItem_description())
                        .item_quantity(menu.getItem_quantity())
                        .price(menu.getPrice())
                        .date(menu.getDate())
                        .menu_imageBase64(getImagebase64(menu.getImage()))
                        .build()

        ));
        return "Admin/viewMenu";
    }


    public String getImagebase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/Menu/";

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

//        Path imagePath = Paths.get(filePath + fileName);
//
//        try {
//            Path symbolicLink = Paths.get("C:/menu/" + fileName); // create a symbolic link to a shorter path
//            if (!symbolicLink.toFile().exists()) {
//                Files.createSymbolicLink(symbolicLink, imagePath);
//            }
//            byte[] bytes = Files.readAllBytes(symbolicLink);
//            String base64 = Base64.getEncoder().encodeToString(bytes);
//            return base64;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
        }




    //contact

    @GetMapping("/contactfetch")
    public String getContactAdmin(Model model) {
        List<Contact> contact = userService.fetchAllContact();
        model.addAttribute("contactlist", contact);


        return "Admin/viewContact";
    }
    @GetMapping("/deleteCon/{id}")
    public String deleteCont(@PathVariable("id") Integer id) {
        userService.deleteContact(id);
        return "redirect:/admin/contactfetch";
    }


    //customer


    @GetMapping("/userDetails")
    public String getUserDetails(org.springframework.ui.Model model)
    {        List<User> users = userService.fetchAllUser();
        model.addAttribute("userList", users.stream().map(user ->
                User.builder()
                        .id(user.getId())
                        .fullname(user.getFullname())
                        .email(user.getEmail())
                        .mobileNo(user.getMobileNo())
                        .build()        ));
        return "Admin/viewCustomer";
    }

    @GetMapping("/deleteCus/{id}")
    public String deleteCust(@PathVariable("id") Integer id) {
        userService.deleteCustomer(id);
        return "redirect:/admin/userDetails";
    }



}

