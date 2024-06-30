package project.petpals.activity.dtos;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import project.petpals.activity.domain.ActivityType;
import project.petpals.company.domain.Company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import lombok.Data;
import project.petpals.company.dtos.CompanyDto;
import project.petpals.location.domain.Location;


@Data
public class NewActivityDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private ActivityType activityType;
    private Location location;

}







