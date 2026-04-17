package com.example.healthportal.Service;

import com.example.healthportal.Model.Doctors;
import com.example.healthportal.Repository.DoctorRepository;
import org.springframework.stereotype.Service;
import com.example.healthportal.Repository.UserRepository;
import com.example.healthportal.Service.SlotService;
import java.time.LocalDate;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final SlotService slotService;

    public DoctorService(DoctorRepository doctorRepository,UserRepository userRepository,SlotService slotService) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.slotService = slotService;
    }
//Slot helper
    public Doctors saveDoctor(Doctors doctor){

        Doctors savedDoctor = doctorRepository.save(doctor);

        System.out.println(" Doctor saved: " + savedDoctor.getId());

        // GENERATE SLOTS FOR NEXT 7 DAYS
        for(int i = 0; i <= 6; i++){
            slotService.generateSlots(savedDoctor.getId(), LocalDate.now().plusDays(i));
        }

        return savedDoctor;
    }


    // Get all doctors
    public List<Doctors> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctors getDoctorById(Long id){
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    // Search doctors
    public List<Doctors> searchDoctors(String name, String specialty) {

        // FIX: handle null + empty values
        name = (name == null) ? "" : name.trim();
        specialty = (specialty == null) ? "" : specialty.trim();

        // DEBUG (optional)
        System.out.println("NAME: " + name);
        System.out.println("SPECIALTY: " + specialty);

// Seaech both
        if(name != null &&  !name.isEmpty()
        && specialty != null && !specialty.isEmpty()){
            return doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyContainingIgnoreCase(name,specialty);
        }

//   Search BY  Name
        if(name != null && !name.isEmpty())
        {
            return doctorRepository.findByNameContainingIgnoreCase(name);
        }

//        Search By Specialty
        if (specialty != null && !specialty.isEmpty()) {
            return doctorRepository.findBySpecialtyContainingIgnoreCase(specialty);
        }


//        Default
        return doctorRepository.findAll();
    }
}