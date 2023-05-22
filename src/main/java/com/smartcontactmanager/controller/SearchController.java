package com.smartcontactmanager.controller;

import com.smartcontactmanager.domain.Contact;
import com.smartcontactmanager.domain.User;
import com.smartcontactmanager.repository.ContactRepository;
import com.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(Principal principal, @PathVariable("query") String query) {

        User user = userRepository.getUserByUserName(principal.getName());

        List<Contact> contacts = contactRepository.findByNameContainingAndUser(query, user);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}