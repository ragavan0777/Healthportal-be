package com.example.healthportal.controller;

import com.example.healthportal.Dto.DoctorRequest;
import com.example.healthportal.Model.Doctors;
import com.example.healthportal.Model.Role;
import com.example.healthportal.Model.User;
import com.example.healthportal.Model.Patient;
import com.example.healthportal.Repository.UserRepository;
import com.example.healthportal.Repository.DoctorRepository;
import com.example.healthportal.Repository.PatientRepository;
import com.example.healthportal.Security.Jwtservice;
import com.example.healthportal.Service.SlotService;
import com.example.healthportal.Service.DoctorService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
public class AuthController {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final Jwtservice jwtService;
    private final SlotService slotService;
    private final DoctorService doctorService;

    // ️ DOCTOR REGISTER

    @PostMapping("/register-doctor")
    public String registerDoctor(@RequestBody DoctorRequest request){

        // Create USER
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.DOCTOR);

        User savedUser = userRepository.save(user);

        // Create DOCTOR
        Doctors doctor = new Doctors();
        doctor.setUserId(savedUser.getId());
        doctor.setName(request.getName());
        doctor.setSpecialty(request.getSpecialty());
        doctor.setExperience(request.getExperience());

         Doctors savedDoctor  =   doctorService.saveDoctor(doctor);

        for(int i = 0; i < 7; i++){
            slotService.generateSlots(savedDoctor.getId(), LocalDate.now().plusDays(i));
        }

        System.out.println("Doctor Registered Successfully");

        return "Doctor registered successfully!";
    }


//       USER REGISTER

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        Patient patient = new Patient();
        patient.setUserId(savedUser.getId());

        patientRepository.save(patient);

        System.out.println("User Registered Successfully");

        return "User registered successfully!";
    }


    // LOGIN (COMMON FOR BOTH)

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        // Find user by email
        User foundUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check password
        if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {

            //  Generate JWT
            return jwtService.generateToken(foundUser);

        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}