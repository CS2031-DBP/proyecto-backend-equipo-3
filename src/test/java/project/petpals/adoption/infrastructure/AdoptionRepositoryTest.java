package project.petpals.adoption.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import project.petpals.AbstractContainerBaseTest;
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.domain.PetPersonId;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;
import project.petpals.pet.infrastructure.PetRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdoptionRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    TestEntityManager entityManager;

    private Pet pet;
    private Person person;
    private Adoption adoption;

    @BeforeEach
    void setup() {
        pet = new Pet();
        pet = new Pet();
        pet.setSex("female");
        pet.setPetStatus(PetStatus.IN_ADOPTION);
        pet.setName("Lola");
        pet.setBreed("poodle");
        pet.setBirthDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        pet.setSpecies(Species.DOG);
        pet.setWeight(4.3);
        entityManager.persist(pet);

        person = new Person();
        person.setUsername("Gadiel Velarde");
        person.setPassword("123456");
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("a@utec.edu.pe");
        entityManager.persist(person);

        adoption = new Adoption();
        adoption.setAdoptionDate(LocalDateTime.of(2024,1,1,1,1));
        adoption.setPerson(person);
        adoption.setPet(pet);
        adoption.setDescription("Helper pet");
        entityManager.flush();

        // VERIFY CREATION OF PETPERSON ID??
        adoption.setId(new PetPersonId(pet.getId(), person.getId()));
        adoptionRepository.save(adoption);

    }

    @Test
    void testCreationAndCustomId() {
        Optional<Adoption> retrievedAdoption = adoptionRepository.findById(adoption.getId());
        assertEquals(retrievedAdoption.get(), adoption);
    }
//
//    void testFindAllAdoptionsOfUser() {
//
//    }
}
