package project.petpals.pet.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

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







}
