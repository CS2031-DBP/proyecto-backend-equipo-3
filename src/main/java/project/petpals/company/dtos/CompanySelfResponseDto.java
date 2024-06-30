package project.petpals.company.dtos;

import lombok.Data;
import project.petpals.location.domain.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompanySelfResponseDto {
    Long id;
    String email;
    String name;
    LocalDateTime created;
    LocalDateTime lastUpdated;
    String ruc;
    List<Location> locations = new ArrayList<>();
}
