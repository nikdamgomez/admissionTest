package com.sprint3.admission_test.application.ports.in;

import com.sprint3.admission_test.domain.model.Medication;
import com.sprint3.admission_test.infrastructure.adapter.dto.NewMedicamentDto;

import java.time.LocalDate;
import java.util.List;

public interface IMedicationUseCase {
    Medication getMedicationById(Long id);
    NewMedicamentDto addNewMedicament(NewMedicamentDto newMedicamentDto);
    List<Medication> getMedicationByCatoegoryAndExpireDate(String category_name, LocalDate expiration_date);
}
