package project.petpals.company.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class CompanyDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String username ;
    @NotNull
    @Size(min = 8, max = 11)
    private String ruc;
}

