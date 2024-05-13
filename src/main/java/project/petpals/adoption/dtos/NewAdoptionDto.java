package project.petpals.adoption.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewAdoptionDto {
    //revisarrrrrrr
    @NotNull
    private Long PetId;
    @NotNull
    private Long PersonId;
    @NotNull
    @Size(min = 1, max = 255)
    private String Description;
}
