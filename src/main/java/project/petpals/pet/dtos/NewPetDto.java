package project.petpals.pet.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class NewPetDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    private LocalDate birthDate;
    @NotNull
    private String sex;
    @NotNull
    @Size(min=2,max=50)
    private String breed;
    @NotNull
    @Positive
    private Double weight;
    @NotNull
    private Species species;
}
