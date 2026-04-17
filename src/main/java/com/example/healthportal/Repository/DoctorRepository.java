package com.example.healthportal.Repository;

import com.example.healthportal.Model.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctors, Long> {

    List<Doctors> findBySpecialtyContainingIgnoreCase(String specialty);

    Optional<Doctors> findByUserId(Long userId);

    List<Doctors> findByNameContainingIgnoreCase(String name);
    List<Doctors> findByNameContainingIgnoreCaseAndSpecialtyContainingIgnoreCase(String name,String specialty);
}