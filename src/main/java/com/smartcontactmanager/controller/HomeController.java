package com.smartcontactmanager.controller;

import com.smartcontactmanager.domain.User;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manager App");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - Smart Contact Manager App");
        return "about";
    }

    @RequestMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Register - Smart Contact Manager App");
        return "signup";
    }

    //handler for registering user
    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {

        try {
            if (!agreement) {
                System.out.println("You have not agreed to the terms and conditions");
                throw new Exception("You have not agreed to the terms and conditions");
            }
            user.setRole("ROLE_USER");
            user.setImageUrl("default.png");
            user.setEnabled(true);

            User result = userRepository.save(user);


            model.addAttribute("user", new User());

            session.setAttribute("message", new Message(" User registered successfully", "alert-success"));
            return "signUp";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong" + e.getMessage(), "alert-danger"));
        }
        return "signup";
    }
}