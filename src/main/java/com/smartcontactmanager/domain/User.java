package com.smartcontactmanager.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name field is required!!")
    @Size(min = 2, max = 20, message = " You can't exceed maximum characters")
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean isEnabled;
    private String imageUrl;
    private String about;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
}