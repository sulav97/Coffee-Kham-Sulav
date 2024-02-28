package com.example.coffee.Controller;

import com.example.coffee.Pojo.ContactPojo;
import com.example.coffee.Pojo.UserPojo;
import com.example.coffee.Services.UserService;
import freemarker.template.Configuration;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

//login and register
    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new UserPojo());
        return "registration";
    }

    @PostMapping("/save")
    public String saveUser(@Valid UserPojo userpojo) {
        userService.save(userpojo);
        return "loginPage";
    }

    //contact page
    @GetMapping("/contact")
    public String getPage( Model model){
        model.addAttribute("contact", new ContactPojo());
        return "contactPage";}

    @PostMapping("/send-message")
    public String submitMessage(@Valid ContactPojo contactPojo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
        userService.submitMsg(contactPojo);
        return "redirect:/user/contact";
    }





    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;



    @GetMapping("/forgotpassword")
    public String forgetpassword(Model model){
        model.addAttribute("users",new UserPojo());
        return ("forgetPassword");
    }

    @GetMapping("/index")
    public String getIndex(){

        return ("Index");
    }
    @PostMapping("/changepassword")
    public String changepassword(@RequestParam("email") String email, Model model, @Valid UserPojo userPojo){
        userService.processPasswordResetRequest(userPojo.getEmail());
        model.addAttribute("message","Your new password has been sent to your email Please " +
                "check your inbox");
        return "redirect:/user/login";
    }
}
