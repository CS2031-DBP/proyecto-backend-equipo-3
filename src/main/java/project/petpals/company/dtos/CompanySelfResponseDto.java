package project.petpals.company.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanySelfResponseDto {
    String email;
    String name;
    LocalDateTime created;
    LocalDateTime lastUpdated;
    String ruc;
}
