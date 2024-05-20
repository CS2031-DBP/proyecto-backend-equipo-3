package project.petpals.adoption.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.petpals.company.domain.Company;
import project.petpals.person.domain.Person;
import project.petpals.pet.domain.Pet;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Adoption {

    @EmbeddedId
    PetPersonId id;

    @Column(name = "description")
    private String description;

    @Column(name = "adoption_date")
    private LocalDateTime adoptionDate;

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    @MapsId("petId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;
}
