package com.example.healthportal.Model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Appointment")
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctors doctor;

  @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

  private  LocalDate date;
  private LocalTime  time;

  @Column(nullable = false)
  private String status;  // pending,completed


  public Appointment()
  {

  }

  public Appointment(Doctors doctor,User patient,LocalDate date,LocalTime time,String status){

      this.doctor = doctor;
      this.patient= patient;
      this.date = date;
      this.time =time;
      this.status = status;
  }

}

