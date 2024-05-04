package project.petpals.pet.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetTest {


    @Test
    void testPetCreation() {
        Pet pet = new Pet();
        pet.setSex("female");
        pet.setPetStatus(PetStatus.ADOPTED);
        pet.setName("Lola");
        pet.setBreed("poodle");
        pet.setBirthDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        pet.setSpecies(Species.DOG);
        pet.setWeight(4.3);

        assertEquals("female", pet.getSex());
        assertEquals(PetStatus.ADOPTED, pet.getPetStatus());
        assertEquals("Lola", pet.getName());
        assertEquals("poodle", pet.getBreed());
        assertEquals(LocalDateTime.of(2020,1,1,0,0), pet.getBirthDate());
        assertEquals(Species.DOG, pet.getSpecies());
        assertEquals(4.3, pet.getWeight());
    }

}
