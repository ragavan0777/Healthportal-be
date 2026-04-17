package com.example.healthportal.Repository;

import com.example.healthportal.Model.Appointment;
import com.example.healthportal.Model.Doctors;
import com.example.healthportal.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    // Prevent double booking
    boolean existsByDoctorAndDateAndTime(
            Doctors doctor,
            LocalDate date,
            LocalTime time
    );

    // Doctor view their appointments
    List<Appointment> findByDoctor(Doctors doctor);

    // Patient view their appointments
    List<Appointment> findByPatient(User patient);

    // Get booked slots
    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
}