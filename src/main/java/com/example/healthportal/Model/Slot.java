package com.example.healthportal.Model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Doctor_slot")
@Data
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name =  "Doctor_id")
    private Doctors doctor;

    private LocalDate date;
    private LocalTime time;

    private  String status;



}
