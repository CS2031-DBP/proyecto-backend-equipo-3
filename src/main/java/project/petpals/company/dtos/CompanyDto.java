package project.petpals.company.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import project.petpals.location.domain.Location;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    @Size(min = 8, max = 11)
    private String ruc;
    List<Location> locations = new ArrayList<>();
}

