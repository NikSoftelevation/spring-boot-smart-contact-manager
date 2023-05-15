package com.smartcontactmanager.controller;

import com.smartcontactmanager.domain.Contact;
import com.smartcontactmanager.domain.User;
import com.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //method for adding common data
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {

        String userName = principal.getName();
        System.out.println(userName);

        //get the user using username(email)

        User user = userRepository.getUserByUserName(userName);

        System.out.println(user);

        model.addAttribute("user", user);
    }

    //dashboard home
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {

        model.addAttribute("title", "User Dashboard");

        return "normal/user_dashboard";
    }

    //open add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    //processing contact form
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, Principal principal) {

        String name = principal.getName();

        User user = userRepository.getUserByUserName(name);

        user.getContacts().add(contact);

        userRepository.save(user);

        return "normal/add_contact_form";
    }
}