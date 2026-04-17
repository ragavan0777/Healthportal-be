package com.example.healthportal.controller;

import com.example.healthportal.Model.Slot;
import com.example.healthportal.Service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin

public class SlotController {



        private final SlotService slotService;

        public SlotController(SlotService slotService){
            this.slotService = slotService;
        }

        // THIS IS WHAT YOUR FRONTEND NEEDS
        @GetMapping("/{doctorId}/{date}")
        public List<Slot> getSlots(
                @PathVariable Long doctorId,
                @PathVariable LocalDate date
        ){
            return slotService.getSlotsByDoctorAndDate(
                    doctorId,
                   date
            );
        }
    }

