package project.petpals.activity.dtos;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import project.petpals.activity.domain.ActivityType;
import project.petpals.company.domain.Company;

import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import lombok.Data;
import project.petpals.company.dtos.CompanyDto;


@Data
public class NewActivityDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    private LocalDateTime endDate;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private ActivityType activityType;

}







