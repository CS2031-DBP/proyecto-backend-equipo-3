package project.petpals.pet.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.petpals.company.domain.Company;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "weight", nullable = false)
    private Double Weight;

    @Column(name = "pet_status", nullable = false)
    private PetStatus petStatus;

    @Column(name = "species", nullable = false)
    private Species species;

    @Column(name = "description", nullable = false)
    String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

}
