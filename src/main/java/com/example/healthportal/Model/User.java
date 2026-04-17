package com.example.healthportal.Model;

import jakarta.persistence.*;
import lombok.Data;

//Register

@Entity
@Table (name = "Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long id;

private String name;

private String email;

private String password;


@Enumerated(EnumType.STRING)
    private Role role;




}
