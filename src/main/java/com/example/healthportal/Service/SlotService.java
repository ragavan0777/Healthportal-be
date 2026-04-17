package com.example.healthportal.Service;

import com.example.healthportal.Model.Doctors;
import com.example.healthportal.Model.Slot;
import com.example.healthportal.Repository.DoctorRepository;
import com.example.healthportal.Repository.SlotRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SlotService {

    private final DoctorRepository doctorRepository;
    private final SlotRepository slotRepository;

    public SlotService(DoctorRepository doctorRepository,
                       SlotRepository slotRepository) {

        this.doctorRepository = doctorRepository;
        this.slotRepository = slotRepository;
    }

    @PostConstruct
    public void initSlots(){
        System.out.println("INITIAL SLOT GENERATION STARTED...");
        generateDailySlots();
    }

// GENERATE SLOTS EVERY DAY FOR TILL CURRENT DATE TO  SEVEN DATE
@Scheduled(cron = "0 0 0 * * ?")
public void generateDailySlots(){

    List<Doctors> doctors = doctorRepository.findAll();

    for(Doctors doctor : doctors){

        for(int i = 0; i <= 6; i++){

            LocalDate date = LocalDate.now().plusDays(i);

            System.out.println("Generating slots for Doctor: "
                    + doctor.getId() + " Date: " + date);

            generateSlots(doctor.getId(), date);
        }
    }
}
    // MAIN SLOT GENERATION METHOD
    public void generateSlots(Long doctorId, LocalDate date){

        Doctors doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // -------- MORNING SESSION (10:00 → 13:00) --------
        LocalTime start = LocalTime.of(10,0);
        LocalTime breakTime = LocalTime.of(13,0);

        while(start.isBefore(breakTime)){
            createSlot(doctor, date, start);
            start = start.plusMinutes(30);
        }

        // -------- AFTERNOON SESSION (14:00 → 16:00) --------
        start = LocalTime.of(14,0);
        LocalTime end = LocalTime.of(16,0);

        while(start.isBefore(end)){
            createSlot(doctor, date, start);
            start = start.plusMinutes(30);
        }
    }

    //  HELPER METHOD (CLEAN + REUSABLE)
    private void createSlot(Doctors doctor, LocalDate date, LocalTime time){

        boolean exists = slotRepository.existsByDoctor_IdAndDateAndTime(
                doctor.getId(),
                date,
                time
        );

        if(!exists){
            Slot slot = new Slot();
            slot.setDoctor(doctor);
            slot.setDate(date);
            slot.setTime(time);
            slot.setStatus("OPEN");

            slotRepository.save(slot);

        }
    }

    public List<Slot> getSlotsByDoctorAndDate(Long doctorId, LocalDate date){
        return slotRepository.findByDoctor_IdAndDateAndStatus(
                doctorId,
                date,
                "OPEN"
        );
    }
    }
