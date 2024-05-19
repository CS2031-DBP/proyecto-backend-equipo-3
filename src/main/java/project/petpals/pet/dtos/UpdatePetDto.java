package project.petpals.pet.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePetDto {
    @NotNull
    @Positive
    private Double weight;
    @NotNull
    @Size(max = 255)
    private String description;
}
