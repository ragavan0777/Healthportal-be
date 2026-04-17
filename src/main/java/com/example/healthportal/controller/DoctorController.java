package com.example.healthportal.controller;
import com.example.healthportal.Model.Appointment;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.healthportal.Service.AppointmentService;
import com.example.healthportal.Model.Doctors;
import com.example.healthportal.Service.DoctorService;
import org.springframework.web.bind.annotation.*;
import com.example.healthportal.Service.SlotService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class DoctorController {
    private final DoctorService doctorservice;
    private final AppointmentService appointmentService;
    private final SlotService slotService;

    public DoctorController(DoctorService doctorservice , AppointmentService appointmentService,SlotService slotService) {
        this.doctorservice = doctorservice;
        this.appointmentService = appointmentService;
        this.slotService = slotService;
    }


    // Allow only USER  ADMIN  DOCtor to access this endpoint
    @GetMapping("/getall")
    public List<Doctors> getAllDoctors() {
        System.out.println("Doctors fetched");
        return doctorservice.getAllDoctors();
    }


//    Doctor Slot generate
@PostMapping("/generate-slots")
public String createSlots(@RequestParam Long doctorId,
                          @RequestParam String date){

    slotService.generateSlots(
            doctorId,
            LocalDate.parse(date)
    );

    return "Slots generated";
}


//    Search By name spec
@GetMapping("/search")
public List<Doctors> searchDoctors(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String specialty
){
    return doctorservice.searchDoctors(name, specialty);
}


    // DOCTOR VIEW APPOINTMENTS
    @GetMapping("/{doctorId}")
    public List<Appointment> getDoctorAppointments(@PathVariable Long doctorId) {

        return appointmentService.getDoctorAppointments(doctorId);
    }

    @GetMapping("/public/{id}")
    public Doctors getDoctorPublic(@PathVariable Long id){
       return  doctorservice.getDoctorById(id);
    }




}

