package com.sprint3.admission_test.application.useCases;

import com.sprint3.admission_test.application.ports.in.IMedicationUseCase;
import com.sprint3.admission_test.application.ports.out.ICategoryRepository;
import com.sprint3.admission_test.application.ports.out.IMedicationRepository;
import com.sprint3.admission_test.domain.exceptions.InvalidDateException;
import com.sprint3.admission_test.domain.exceptions.NotFoundException;
import com.sprint3.admission_test.domain.model.Category;
import com.sprint3.admission_test.domain.model.Medication;
import com.sprint3.admission_test.infrastructure.adapter.dto.NewMedicamentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MedicationUseCaseImpl implements IMedicationUseCase {

    @Autowired
    private IMedicationRepository medicationRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Could not find medication with ID: " + id
        ));
    }

    @Override
    public NewMedicamentDto addNewMedicament(NewMedicamentDto newMedicamentDto) {


        //finding the category by name
        Category category = categoryRepository.findByName(newMedicamentDto.getCategory_name()).orElseThrow(() -> new NotFoundException(
                "Could not find category with name: " + newMedicamentDto.getName()
        ));


        //checking if the date of expiration is after the day is added
        if(LocalDate.now().isAfter(newMedicamentDto.getExpiration_date()) || LocalDate.now().isEqual(newMedicamentDto.getExpiration_date())){
            throw new InvalidDateException("Expiration date should be after the date of registry of the medication");
        }

        //setting medication entity
        Medication medication = new Medication();
        medication.setCategory(category);
        medication.setName(newMedicamentDto.getName());
        medication.setPrice(BigDecimal.valueOf(newMedicamentDto.getPrice()));
        medication.setDescription(newMedicamentDto.getDescription());
        medication.setExpirationDate(newMedicamentDto.getExpiration_date());


        //adding medication to table
        Medication medicationResponse = medicationRepository.save(medication);

        //returning dto with id
        return new NewMedicamentDto(medicationResponse.getId(), medicationResponse.getName(), medicationResponse.getDescription(), medicationResponse.getPrice().doubleValue(),medicationResponse.getExpirationDate(),medicationResponse.getCategory().getName() );
    }

    @Override
    public List<Medication> getMedicationByCatoegoryAndExpireDate(String category_name, LocalDate expiration_date) {

        //finding category by name
        Category category = categoryRepository.findByName(category_name).orElseThrow(() -> new NotFoundException(
                "Could not find category with name: " + category_name
        ));

        //retunring list using a jpa query
        return medicationRepository.findByCategoryandAfterExpiracionDate(category, expiration_date);
    }
}
