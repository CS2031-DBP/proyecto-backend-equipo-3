package project.petpals.person.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonSelfResponseDto {
    String name;
    String email;
    LocalDateTime created;
    LocalDateTime lastUpdated;
}
