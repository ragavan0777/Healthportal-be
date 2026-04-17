package com.example.healthportal.Dto;

import lombok.Data;
@Data
public class DoctorRequest {
    private String name;
     private String email;
  private String password;
    private String specialty;
    private Integer experience;
}