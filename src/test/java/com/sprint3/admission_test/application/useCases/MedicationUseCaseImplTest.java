package com.sprint3.admission_test.application.useCases;

import com.sprint3.admission_test.application.ports.out.ICategoryRepository;
import com.sprint3.admission_test.application.ports.out.IMedicationRepository;
import com.sprint3.admission_test.domain.model.Category;
import com.sprint3.admission_test.domain.model.Medication;
import com.sprint3.admission_test.infrastructure.adapter.dto.NewMedicamentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicationUseCaseImplTest {
    @Mock
    private IMedicationRepository medicationRepository;

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private MedicationUseCaseImpl medicationUseCase;

    private static final String TEST_CATEGORY = "Analgesics";
    private static final LocalDate TEST_DATE = LocalDate.parse("2026-08-20");

    private NewMedicamentDto medicamentDto;
    private Medication medicationWithoutID;
    private Medication medication;
    private Category category;

    @BeforeEach
    void setUp(){
        category = new Category();
        category.setId(3L);
        category.setName("Antibiotic");

        medicamentDto = new NewMedicamentDto();
        medicamentDto.setName("Aspirin");
        medicamentDto.setDescription("Used to reduce pain, fever, or inflammation.");
        medicamentDto.setPrice(5.99);
        medicamentDto.setCategory_name(TEST_CATEGORY);
        medicamentDto.setExpiration_date(TEST_DATE);

        medication = new Medication();
        medication.setExpirationDate(TEST_DATE);
        medication.setId(20L);
        medication.setName("Mox test");
        medication.setPrice(BigDecimal.valueOf(20.22));
        medication.setDescription("This is a test medication on the database, for a mox test");
        medication.setCategory(category);

        medicationWithoutID = new Medication();
        medicationWithoutID.setExpirationDate(TEST_DATE);
        medicationWithoutID.setName("Mox test");
        medicationWithoutID.setPrice(BigDecimal.valueOf(20.22));
        medicationWithoutID.setDescription("This is a test medication on the database, for a mox test");
        medicationWithoutID.setCategory(category);
    }

    @Test
    public void addMedicationSuccess(){
        // Mock the find category on the repository
        when(categoryRepository.findByName(medicamentDto.getCategory_name()))
                .thenReturn(Optional.of(category));

        // Mock the save method using any(Medication.class) instead of an exact instance
        when(medicationRepository.save(any(Medication.class))).thenReturn(medication);

        // Call the use case method
        NewMedicamentDto newMedicamentDtoResponse = medicationUseCase.addNewMedicament(medicamentDto);

        // Verify that the methods were called once with the correct types
        verify(medicationRepository, times(1)).save(any(Medication.class));
        verify(categoryRepository, times(1)).findByName(eq(medicamentDto.getCategory_name()));

        // Assert that the data returned matches the mock response (medication)
        assertNotNull(newMedicamentDtoResponse);
        assertEquals(20L, newMedicamentDtoResponse.getId());
        assertEquals(category.getName(), newMedicamentDtoResponse.getCategory_name());
    }

    @Test
    public void addMedicationCategoryNotFoundThrowsException() {
        // Mock the category repository to return empty, simulating a missing category
        when(categoryRepository.findByName(medicamentDto.getCategory_name()))
                .thenReturn(Optional.empty());

        // Expect an exception when running the usecase (adjust exception type if needed)
        assertThrows(RuntimeException.class, () -> {
            medicationUseCase.addNewMedicament(medicamentDto);
        });

        // Verify save was never called because the category validation failed
        verify(categoryRepository, times(1)).findByName(medicamentDto.getCategory_name());
        verify(medicationRepository, never()).save(any(Medication.class));
    }





}