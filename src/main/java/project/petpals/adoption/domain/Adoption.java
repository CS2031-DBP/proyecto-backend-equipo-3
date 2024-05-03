package project.petpals.adoption.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import project.petpals.person.domain.Person;
import project.petpals.pet.domain.Pet;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Adoption {
    @EmbeddedId
    private PetPersonId id;

    public Adoption() {}

    @Column(name = "description")
    private String description;

    @Column(name = "adoption_date")
    private LocalDateTime adoptionDate;

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @MapsId("petId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;
}
