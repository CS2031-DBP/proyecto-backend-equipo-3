package project.petpals.adoption.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewAdoptionDto {
    @NotNull
    @Size(min = 1, max = 255)
    private String description;
}
