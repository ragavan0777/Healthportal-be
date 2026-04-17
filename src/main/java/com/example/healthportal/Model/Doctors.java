package com.example.healthportal.Model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "doctors")
@Data
public class Doctors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
private  String name;
    private String specialty;
    private int experience;

}