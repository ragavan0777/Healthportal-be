package com.example.healthportal.Service;

import com.example.healthportal.Model.Appointment;
import com.example.healthportal.Model.Doctors;
import com.example.healthportal.Model.Slot;
import com.example.healthportal.Model.User;
import com.example.healthportal.Repository.AppointmentRepository;
import com.example.healthportal.Repository.DoctorRepository;
import com.example.healthportal.Repository.UserRepository;
import com.example.healthportal.Repository.SlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final SlotRepository slotRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            SlotRepository slotRepository
             ) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.slotRepository = slotRepository;
    }


    public List<String> getBookedSlots(Long doctorId, String date){
   LocalDate parsedDate = LocalDate.parse(date);

        List<Appointment> appointments =
                appointmentRepository.findByDoctorIdAndDate(doctorId, parsedDate);

        return appointments.stream()
                .map(app -> app.getTime().toString())
                .toList();
    }

    // DOCTOR APPROVE
    public Appointment approveAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("APPROVED");

        return appointmentRepository.save(appointment);
    }

    // DOCTOR REJECT
    public Appointment rejectAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("REJECTED");

        return appointmentRepository.save(appointment);
    }
// SET AS COMPLETED
    public Appointment completeAppointment(Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Status not completed"));

        appointment.setStatus("COMPLETED");
        return appointmentRepository.save(appointment);
    }

    // DOCTOR VIEW APPOINTMENTS
// FIX: ignore doctorId param completely
    public List<Appointment> getDoctorAppointments(Long doctorId) {

        // FIX: get logged-in doctor
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Long userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        Doctors doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentRepository.findByDoctor(doctor);
    }

    // PATIENT VIEW APPOINTMENTS
    public List<Appointment> getPatientAppointments(Long patientId) {

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return appointmentRepository.findByPatient(patient);
    }

    public Appointment bookSlot(Long slotId, String date){


            // Get logged-in user from JWT
            String email = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            User patient = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Slot slot = slotRepository.findById(slotId)
                    .orElseThrow(() -> new RuntimeException("Slot not found"));

            if(!slot.getStatus().equals("OPEN")){
                throw new RuntimeException("Slot already booked");
            }

            Appointment appointment = new Appointment(
                    slot.getDoctor(),
                    patient,
                    slot.getDate(),
                    slot.getTime(),
                    "PENDING"
            );

            slot.setStatus("BOOKED");
            slotRepository.save(slot);

            return appointmentRepository.save(appointment);
        }
    }

