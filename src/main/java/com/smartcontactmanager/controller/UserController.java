package com.smartcontactmanager.controller;

import com.smartcontactmanager.domain.Contact;
import com.smartcontactmanager.domain.User;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.repository.ContactRepository;
import com.smartcontactmanager.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

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
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Principal principal, HttpSession session) {

        try {
            String name = principal.getName();

            User user = userRepository.getUserByUserName(name);

            //processing and uploading file

            if (file.isEmpty()) {
                System.out.println("File is empty");

                contact.setImage("contact.png");

            } else {
                //upload the file to folder and update the name to contact

                contact.setImage(file.getOriginalFilename());

                File saveFile = new ClassPathResource("static/image").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }
            user.getContacts().add(contact);
            userRepository.save(user);

            session.setAttribute("message", new Message("Your Contact is added !", "success"));
        } catch (Exception e) {
            session.setAttribute("message", new Message("Something went wrong ! Please try again", "danger"));
            System.out.println("ERROR " + e.getMessage());
            e.printStackTrace();
        }

        return "normal/add_contact_form";
    }

    //show contacts
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") int page, Model model, Principal principal) {
        model.addAttribute("title", "Show User Contacts");

        String userName = principal.getName();

        User user = userRepository.getUserByUserName(userName);

        //currentPage-page
        //contacts per page-5
        Pageable pageable = PageRequest.of(page, 3);

        Page<Contact> contacts = contactRepository.findContactsByUser(user.getId(), pageable);

        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contacts.getTotalPages());

        return "normal/show_contacts";
    }

    //showing particular contact details
    @GetMapping("/{cId}/contact")
    public String showContactDetail(@PathVariable("cId") int cId, Model model, Principal principal) {

        Contact contact = contactRepository.findById(cId).orElseThrow(() -> new RuntimeException());

        String userName = principal.getName();

        User user = userRepository.getUserByUserName(userName);

        if (user.getId() == contact.getUser().getId()) {
            model.addAttribute("contact", contact);
            model.addAttribute("title", contact.getCName());
        }
        return "normal/contact_detail";
    }
}