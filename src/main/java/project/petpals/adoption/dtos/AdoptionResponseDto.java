package project.petpals.adoption.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.petpals.adoption.domain.PetPersonId;
import project.petpals.pet.dtos.PetDto;

import java.time.LocalDate;

@Data
public class AdoptionResponseDto {
    @NotNull
    PetPersonId id;

    @NotNull
    String description;

    @NotNull
    LocalDate adoptionDate;

    @NotNull
    PetDto petDto;
}
