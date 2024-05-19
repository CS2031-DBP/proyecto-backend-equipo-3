package project.petpals.pet.infrastructure;

import project.petpals.AbstractContainerBaseTest;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PetRepositoryTest extends AbstractContainerBaseTest {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    TestEntityManager entityManager;

    private Pet pet;
    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setName("UTEC");
        company.setRuc("12345678");
        company.setEmail("utec@gmail.com");
        company.setPassword("12345678");
        company.setCreated(LocalDateTime.of(2020,1,1,1,1));
        company.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        company.setRole(Role.COMPANY);
        entityManager.persist(company);

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
        entityManager.persist(pet);

        entityManager.flush();
    }

    @Test
    void testCreation() {
        Pet foundPet = petRepository.findById(this.pet.getId()).orElse(null);
        assertNotNull(foundPet);

        assertEquals("Firulais", foundPet.getName());
        assertEquals("German shepherd", foundPet.getBreed());
        assertEquals(PetStatus.IN_ADOPTION, foundPet.getPetStatus());
        assertEquals(LocalDate.of(2020,1,1), foundPet.getBirthDate());
        assertEquals("male", foundPet.getSex());
        assertEquals(34.2, foundPet.getWeight());
        assertEquals(Species.DOG, foundPet.getSpecies());
    }

    @Test
    void testCompany() {
        Pet foundPet = petRepository.findById(this.pet.getId()).orElse(null);
        assertNotNull(foundPet);

        assertEquals(company, foundPet.getCompany());
        assertNotNull(foundPet.getCompany().getId());
        assertEquals("utec@gmail.com", foundPet.getCompany().getEmail());
    }
    @Test
    void testFindAllByCompanyId() {
        Pageable page = PageRequest.of(0,5);
        Page<Pet> petPage = petRepository.findAllByCompanyIdAndPetStatus(company.getId(), PetStatus.IN_ADOPTION, page);
        assertEquals(1, petPage.getTotalElements());
        assertEquals(pet, petPage.getContent().get(0));
    }
    @Test
    void testFindAllBySpecies() {
        Pageable page = PageRequest.of(0,5);
        Pageable page2 = PageRequest.of(0,5);

        Page<Pet> catPage = petRepository.findAllBySpeciesAndPetStatus(Species.CAT, PetStatus.IN_ADOPTION, page2);
        Page<Pet> dogPage = petRepository.findAllBySpeciesAndPetStatus(Species.DOG, PetStatus.IN_ADOPTION, page);

        assertEquals(1, dogPage.getTotalElements());
        assertEquals(pet, dogPage.getContent().get(0));
        assertEquals(0,catPage.getTotalElements());
    }

    @Test
    void testFindAllByBreed() {
        Pageable page = PageRequest.of(0,5);
        Page<Pet> dogPage = petRepository.findALlByBreedIgnoreCaseAndPetStatus("German Shepherd", PetStatus.IN_ADOPTION, page);

        assertEquals(1, dogPage.getTotalElements());
        assertEquals(pet, dogPage.getContent().get(0));

    }

    @Test
    void testFindAllByPetStatus() {
        Pageable page = PageRequest.of(0,5);
        Page<Pet> dogPage = petRepository.findAllByPetStatus(PetStatus.IN_ADOPTION, page);

        assertEquals(1, dogPage.getTotalElements());
        assertEquals(pet, dogPage.getContent().get(0));
    }

    @Test
    void testDeleteByIdAndKeepCompany() {
        petRepository.deleteById(pet.getId());
        entityManager.flush();
        entityManager.clear();
        Company foundCompany = companyRepository.findById(company.getId()).orElse(null);
        assertNotNull(foundCompany);
        assertEquals(0, petRepository.findAllByCompanyIdAndPetStatus(company.getId(), PetStatus.IN_ADOPTION, PageRequest.of(0,5)).getTotalElements());
    }

}
