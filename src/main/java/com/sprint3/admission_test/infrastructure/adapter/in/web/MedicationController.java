package com.sprint3.admission_test.infrastructure.adapter.in.web;

import com.sprint3.admission_test.application.ports.in.IMedicationUseCase;
import com.sprint3.admission_test.domain.model.Medication;
import com.sprint3.admission_test.infrastructure.adapter.dto.NewMedicamentDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    @Autowired
    private IMedicationUseCase medicationUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(medicationUseCase.getMedicationById(id));
    }

    @PostMapping
    public ResponseEntity<NewMedicamentDto> addNewMedication(@Valid @RequestBody NewMedicamentDto newMedicamentDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationUseCase.addNewMedicament(newMedicamentDto));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Medication>> getMedicationByCatoegoryAndExpireDate(@PathVariable String category, @RequestParam LocalDate expiration_date){
        return ResponseEntity.status(HttpStatus.OK).body(medicationUseCase.getMedicationByCatoegoryAndExpireDate(category,expiration_date));
    }

}
