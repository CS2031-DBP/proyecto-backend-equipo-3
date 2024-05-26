package project.petpals.pet.domain;

import project.petpals.company.domain.Company;
import project.petpals.location.domain.Location;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetTest {

    private Pet pet;
    private Company company;
    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setAddress("Jr Medrano Silva");
        location.setLatitude(-40.5);
        location.setLongitude(12.34);

        company = new Company();
        company.setName("UTEC");
        company.setRuc("12345678");
        company.setEmail("utec@gmail.com");
        company.setPassword("12345678");
        company.setCreated(LocalDateTime.of(2020,1,1,1,1));
        company.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        company.setRole(Role.COMPANY);
        company.getLocations().add(location);

        pet = new Pet();
        pet.setPetStatus(PetStatus.IN_ADOPTION);
        pet.setName("Firulais");
        pet.setBreed("German shepherd");
        pet.setCompany(company);
        pet.setBirthDate(LocalDate.of(2020,1,1));
        pet.setSex("male");
        pet.setWeight(34.2);
        pet.setDescription("A very good dog. Likes to play fetch.");
        pet.setSpecies(Species.DOG);
    }

    @Test
    void testCreation() {
        assertEquals("Firulais", pet.getName());
        assertEquals("German shepherd", pet.getBreed());
        assertEquals(company, pet.getCompany());
        assertEquals("male", pet.getSex());
        assertEquals(34.2, pet.getWeight());
        assertEquals(LocalDate.of(2020,1,1), pet.getBirthDate());
        assertEquals(Species.DOG, pet.getSpecies());
        assertEquals(PetStatus.IN_ADOPTION, pet.getPetStatus());
    }

    @Test
    void testCompany() {
        assertEquals("utec@gmail.com", pet.getCompany().getEmail());
        assertEquals(location, pet.getCompany().getLocations().get(0));
    }
}
