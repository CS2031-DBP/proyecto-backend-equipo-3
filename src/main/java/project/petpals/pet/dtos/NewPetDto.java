package project.petpals.pet.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import project.petpals.pet.domain.Species;

import java.time.LocalDate;

@Data
public class NewPetDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
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
//    @NotNull
//    @Positive
//    private Long companyId;
    @NotNull
    @Size(min = 2, max = 255)
    private String description;

//    @NotNull
    private String image;
}
