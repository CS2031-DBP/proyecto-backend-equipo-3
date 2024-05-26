package project.petpals.activity.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.petpals.activity.domain.ActivityStatus;
import project.petpals.activity.domain.ActivityType;
import project.petpals.company.domain.Company;
import project.petpals.company.dtos.CompanyDto;
import project.petpals.location.domain.Location;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ActivityResponseDto {
    @NotNull
    private String name;
    @NotNull
    Long id;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private ActivityStatus activityStatus;
    @NotNull
    private ActivityType activityType;
    @NotNull
    private CompanyDto companyDto;
    List<Location> locations = new ArrayList<>();
}
