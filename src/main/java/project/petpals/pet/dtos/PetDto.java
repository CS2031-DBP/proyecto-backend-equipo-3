package project.petpals.pet.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PetDto {
    // added
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    private String sex;
    @NotNull
    @Size(min=2,max=50)
    private String breed;
//    @NotNull
    private String image;
    //added
    private String description;
}
