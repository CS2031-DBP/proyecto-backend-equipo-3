package project.petpals.person.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class PersonDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    private String profileImage;
    private String bannerImage;
}
