package project.petpals.pet.infrastructure;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.petpals.AbstractContainerBaseTest;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PetRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private EntityManager entityManager;
    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setSex("female");
        pet.setPetStatus(PetStatus.IN_ADOPTION);
        pet.setName("Lola");
        pet.setBreed("poodle");
        pet.setBirthDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        pet.setSpecies(Species.DOG);
        pet.setWeight(4.3);
        entityManager.persist(pet);
    }

    @Test
    void testPetCreation() {

        entityManager.flush();
        entityManager.clear();

        Optional<Pet> createdPet = petRepository.findById(pet.getId());
        assertTrue(createdPet.isPresent());
    }

    @Test
    void testPetStatusUpdate() {
        entityManager.flush();
        entityManager.clear();

        Optional<Pet> createdPet = petRepository.findById(pet.getId());
        assertTrue(createdPet.isPresent());

        createdPet.get().setPetStatus(PetStatus.ADOPTED);
        petRepository.save(createdPet.get());

        Optional<Pet> updatedPet = petRepository.findById(pet.getId());
        assertEquals(updatedPet.get().getPetStatus(), PetStatus.ADOPTED);
    }

    @Test
    void testGetAllBySpecies() {
        entityManager.flush();
        entityManager.clear();

        Pageable page  = PageRequest.of(0,3);

        Page<Pet> res = petRepository.findAllBySpecies(Species.DOG, page);

        assertEquals(1,res.getTotalElements());
        assertEquals(res.stream().findFirst().get(), pet);
    }

    @Test
    void testFindAllByBreed() {
        entityManager.flush();
        entityManager.clear();

        Pageable page = PageRequest.of(0,3);
        Page<Pet> res = petRepository.findAllByBreed("poodle", page);

        assertEquals(1,res.getTotalElements());
        assertEquals(res.stream().findFirst().get(), pet);
    }

    @Test
    void testFindAllByInvalidBreedAndReturnEmptyPage() {
        entityManager.flush();
        entityManager.clear();

        Pageable page = PageRequest.of(0,3);
        Page<Pet> res = petRepository.findAllByBreed("doodle", page);

        assertTrue(res.isEmpty());
    }

}


/* WHAT SHOULD THE REPOSITORY DO?
* - Create a pet and save all of its attributes
* - Find all by species (page)
* - Find all by pet status
* */

/*pet.setSex("female");
        pet.setPetStatus(PetStatus.IN_ADOPTION);
        pet.setName("Lola");
        pet.setBreed("poodle");
        pet.setBirthDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        pet.setSpecies(Species.DOG);
        pet.setWeight(4.3);*/