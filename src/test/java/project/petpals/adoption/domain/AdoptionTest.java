package project.petpals.adoption.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.petpals.person.domain.Person;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdoptionTest {

    private Person person;
    private Pet pet;
    private Adoption adoption;

    @BeforeEach
    void setup() {
        person = new Person();
        person.setUsername("Gadiel Velarde");
        person.setPassword("123456");
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("a@utec.edu.pe");

        pet = new Pet();
        pet.setSex("female");
        pet.setPetStatus(PetStatus.ADOPTED);
        pet.setName("Lola");
        pet.setBreed("poodle");
        pet.setBirthDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        pet.setSpecies(Species.DOG);
        pet.setWeight(4.3);

        adoption = new Adoption();
        adoption.setPerson(person);
        adoption.setPet(pet);
        adoption.setAdoptionDate(LocalDateTime.of(2021,1,1,1,1));
    }

    @Test
    void testAdoption() {
        assertEquals(person, adoption.getPerson());
        assertEquals(pet, adoption.getPet());
        assertEquals(LocalDateTime.of(2021,1,1,1,1), adoption.getAdoptionDate());
    }
}
