package com.sprint3.admission_test.infrastructure.adapter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.IMessage;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMedicamentDto {


    private Long id;

    @NotNull(message = "nombre no puede estar vacio")
    @Size(min=5, max = 100, message = "Nombre debe tener entre 5 y 100 caracteres")
    private String name;

    @NotNull(message = "descripcion no puede estar vacio")
    @Size(min=30, max = 255, message = "Descripcion debe tener entre 30 y 255 caracteres")
    private String description;

    @NotNull(message = "precio no puede estar vacio")
    @DecimalMin(value = "0.01" , message = "El precio tiene que ser mayor que 0")
    @Digits(integer = 12, fraction = 2, message = "El precio tiene que tener maximo 12 numeros enteros y 2 digitos decimales")
    private double price;

    @NotNull(message = "fecha de expiracion no puede estar vacio")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiration_date;

    @NotNull(message = "nombre de categoria no puede estar vacio")
    @Size(min=3, max = 100, message = "Nombre debe tener entre 5 y 100 caracteres")
    private String category_name;


}
