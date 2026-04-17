package com.example.healthportal.Repository;
import com.example.healthportal.Model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

        @Repository
        public interface SlotRepository extends JpaRepository<Slot,Long> {

            List<Slot> findByDoctor_IdAndDateAndStatus(Long doctorId, LocalDate date, String status);

//to prevent duplicate slots
            boolean existsByDoctor_IdAndDateAndTime(Long doctorId, LocalDate date, LocalTime time);

        }


