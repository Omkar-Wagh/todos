//package com.demo.controller;
//
//import com.demo.model.User;
//import com.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Controller
//public class UserController {
//    @Autowired
//    private UserService service;
//
//    @PostMapping("/login")
//    public String loginPage(@RequestBody User user) {
//        if (user != null) {
//            return "redirect:/"; // If already logged in, go to dashboard
//        }
//        return "login"; // Show login page if not authenticated
//    }
//
//    @PostMapping("/register")
//    public String register(@RequestBody User user){
//        service.saveUser(user);
//        return "redirect:/";
//    }
//
//
//}
package com.demo.controller;

import com.demo.model.User;
import com.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
//    @PostMapping("/login")
//    public String login(@ModelAttribute User user, Model model) {
//        User dbUser = service.findByUsername(user.getUsername());
//
//        if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
//            model.addAttribute("user", dbUser);
//            return "user-dashboard";
//        } else {
//            model.addAttribute("error", "Invalid credentials");
//            return "login";
//        }
//    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        service.saveUser(user);
        return "redirect:/login";
    }
}
