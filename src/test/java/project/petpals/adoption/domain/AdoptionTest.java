package project.petpals.adoption.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.petpals.company.domain.Company;
import project.petpals.person.domain.Person;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;
import project.petpals.user.domain.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdoptionTest {

    private Person person;
    private Pet pet;
    private Company company;
    private Adoption adoption;
    private PetPersonId adoptionId;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setName("UTEC");
        company.setRuc("12345678");
        company.setEmail("utec@gmail.com");
        company.setPassword("12345678");
        company.setCreated(LocalDateTime.of(2020, 1, 1, 1, 1));
        company.setLastUpdated(LocalDateTime.of(2020, 1, 1, 1, 1));
        company.setRole(Role.COMPANY);

        person = new Person();
        person.setId(2L);
        person.setCreated(LocalDateTime.of(2020, 1, 1, 1, 1));
        person.setLastUpdated(LocalDateTime.of(2020, 1, 1, 1, 1));
        person.setEmail("joaquin@gmail.com");
        person.setPassword("12345678");
        person.setName("Joaquin");
        person.setRole(Role.PERSON);

        pet = new Pet();
        pet.setPetStatus(PetStatus.IN_ADOPTION);
        pet.setName("Firulais");
        pet.setBreed("German shepherd");
        pet.setCompany(company);
        pet.setBirthDate(LocalDate.of(2020,1,1));
        pet.setSex("male");
        pet.setWeight(34.2);
        pet.setSpecies(Species.DOG);

        adoptionId = new PetPersonId(pet.getId(), person.getId());

        adoption = new Adoption();
        adoption.setId(adoptionId);
        adoption.setAdoptionDate(LocalDate.of(2020, 1, 1));
        adoption.setDescription("Happy pet");
        adoption.setCompany(company);
        adoption.setPet(pet);
        adoption.setPerson(person);
    }

    @Test
    void testCreation() {
        assertEquals(LocalDate.of(2020, 1,  1), adoption.getAdoptionDate());
        assertEquals("Happy pet", adoption.getDescription());
        assertEquals(company, adoption.getCompany());
        assertEquals(pet, adoption.getPet());
        assertEquals(person, adoption.getPerson());
    }

    @Test
    void testGetId() {
        assertEquals(adoptionId, adoption.getId());
    }

    @Test
    void testOtherEntities() {
        assertEquals("UTEC", adoption.getCompany().getName());
        assertEquals("Joaquin", adoption.getPerson().getName());
        assertEquals("Firulais", adoption.getPet().getName());
    }
}
