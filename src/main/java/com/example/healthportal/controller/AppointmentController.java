package com.example.healthportal.controller;
import com.example.healthportal.Model.Appointment;
import com.example.healthportal.Service.AppointmentService;
import com.example.healthportal.Service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final SlotService slotService;

    public AppointmentController(AppointmentService appointmentService,SlotService slotService) {
        this.appointmentService = appointmentService;
        this.slotService = slotService;
    }

//    --------------------------------------slot generate---------------------------------------------------------------
@GetMapping("/booked-slots/{doctorId}/{date}")
public List<String> getBookedSlots(@PathVariable Long doctorId,
                                   @PathVariable String date){
    return appointmentService.getBookedSlots(doctorId, date);
}


//------------------------------------------Patient Book Appointment----------------------------------------------------------------


    @PostMapping("/book-slot")
    public Appointment bookSlot(@RequestParam Long slotId,
                                @RequestParam String date){

        return appointmentService.bookSlot(slotId,date);
    }

//    ----------------------------------------Doctor Approve/reject----------------------------------------------------------
    @PutMapping("/approve/{id}")
    public Appointment approveAppointment(
            @PathVariable Long id
    ) {
        return appointmentService.approveAppointment(id);
    }

    @PutMapping("/reject/{id}")
    public Appointment rejectAppointment(
            @PathVariable Long id
    ) {
        return appointmentService.rejectAppointment(id);
    }

    @PutMapping("/completed/{id}")
    public  Appointment completeAppointment(
            @PathVariable Long id
    ){
        return appointmentService.completeAppointment(id);
    }

    @PostMapping("/generate-slots")
    public String generateSlots(
            @RequestParam Long doctorId,
            @RequestParam String date){

        slotService.generateSlots(
                doctorId,
                LocalDate.parse(date)
        );

        return "Slots generated";
    }

}