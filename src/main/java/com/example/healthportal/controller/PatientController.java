package com.example.healthportal.controller;

import com.example.healthportal.Model.Appointment;
import com.example.healthportal.Model.Slot;
import com.example.healthportal.Service.SlotService;
import com.example.healthportal.Service.AppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PatientController {

    private final AppointmentService appointmentService;
    private final SlotService slotService;

    public PatientController(AppointmentService appointmentService,
                             SlotService slotService) {
        this.appointmentService = appointmentService;
        this.slotService = slotService;
    }

//    -------------------------------------Available Slots---------------------------------------------
//    @GetMapping("/slots/{doctorId}/{date}")
//    public List<Slot> findByDoctor_IdAndDateAndStatus(@PathVariable Long doctorId,@PathVariable String date){
//        LocalDate parseddate = LocalDate.parse(date);
//        return slotService.getAvailableSlots(doctorId,parseddate);
//    }

    // PATIENT VIEW APPOINTMENTS
    @GetMapping("/appointments/{patientId}")
    public List<Appointment> viewMyAppointments(@PathVariable Long patientId) {

        return appointmentService.getPatientAppointments(patientId);
    }
}