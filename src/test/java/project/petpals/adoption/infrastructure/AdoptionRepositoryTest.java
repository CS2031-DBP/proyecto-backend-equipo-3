package project.petpals.adoption.infrastructure;

import org.junit.jupiter.api.AfterEach;
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.domain.PetPersonId;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;
import project.petpals.pet.infrastructure.PetRepository;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.junit.jupiter.Testcontainers;
import project.petpals.AbstractContainerBaseTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdoptionRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private AdoptionRepository adoptionRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    TestEntityManager entityManager;


    private Person person;
    private Pet pet;
    private Company company;
    private Adoption adoption;
    private PetPersonId adoptionId;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setName("UTEC");
        company.setRuc("12345678");
        company.setEmail("utec@gmail.com");
        company.setPassword("12345678");
        company.setCreated(LocalDateTime.of(2020, 1, 1, 1, 1));
        company.setLastUpdated(LocalDateTime.of(2020, 1, 1, 1, 1));
        company.setRole(Role.COMPANY);
        entityManager.persist(company);

        person = new Person();
        person.setCreated(LocalDateTime.of(2020, 1, 1, 1, 1));
        person.setLastUpdated(LocalDateTime.of(2020, 1, 1, 1, 1));
        person.setEmail("joaquin@gmail.com");
        person.setPassword("12345678");
        person.setName("Joaquin");
        person.setRole(Role.PERSON);
        entityManager.persist(person);

        pet = new Pet();
        pet.setPetStatus(PetStatus.IN_ADOPTION);
        pet.setName("Firulais");
        pet.setBreed("German shepherd");
        pet.setCompany(company);
        pet.setBirthDate(LocalDate.of(2020,1,1));
        pet.setSex("male");
        pet.setWeight(34.2);
        pet.setSpecies(Species.DOG);
        pet.setDescription("A very good dog. Likes to play fetch.");
        entityManager.persist(pet);

        adoptionId = new PetPersonId(pet.getId(), person.getId());

        adoption = new Adoption();
        adoption.setId(adoptionId);
        adoption.setAdoptionDate(LocalDate.of(2020,  1, 1));
        adoption.setDescription("Happy pet");
        adoption.setCompany(company);
        adoption.setPet(pet);
        adoption.setPerson(person);
        entityManager.persist(adoption);

        entityManager.flush();
    }

    @AfterEach
    void clearDataBase() {
        adoptionRepository.deleteAll();
        petRepository.deleteAll();
    }

    @Test
    void testCreation() {
        Adoption foundAdoption = adoptionRepository.findById(adoptionId).orElse(null);
        assertNotNull(foundAdoption);
        assertEquals(LocalDate.of(2020, 1,  1), foundAdoption.getAdoptionDate());
        assertEquals("Happy pet", foundAdoption.getDescription());
        assertEquals(company, foundAdoption.getCompany());
        assertEquals(pet, foundAdoption.getPet());
        assertEquals(person, foundAdoption.getPerson());
    }

    @Test
    void testDeleteByIdAndKeepOtherEntities() {
        adoptionRepository.deleteById(adoptionId);
        assertNotNull(personRepository.findById(person.getId()).orElse(null));
        assertNotNull(petRepository.findById(pet.getId()).orElse(null));
        assertNotNull(companyRepository.findById(company.getId()).orElse(null));
    }


}
