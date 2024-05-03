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

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "sex")
    private String sex;

    @Column(name = "breed")
    private String breed;

    @Column(name = "weight")
    private Double Weight;

    @Column(name = "pet_status")
    private PetStatus petStatus;

    @Column(name = "species")
    private Species species;







}
