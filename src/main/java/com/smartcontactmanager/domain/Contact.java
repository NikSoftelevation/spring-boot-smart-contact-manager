package com.smartcontactmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cId;
    private String cName;
    private String secondName;
    private String work;
    private String email;
    private String phone;
    private String image;
    private String description;
    @ManyToOne
    @JsonIgnore
    private User user;
}
