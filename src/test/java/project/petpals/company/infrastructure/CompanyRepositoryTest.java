package project.petpals.company.infrastructure;

import project.petpals.AbstractContainerBaseTest;
import project.petpals.company.domain.Company;
import project.petpals.location.domain.Location;
import project.petpals.location.infrastructure.LocationRepository;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    TestEntityManager entityManager;

    private Company company;
    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setAddress("Calle Ponce de Leon 123");
        location.setLatitude(-40.5);
        location.setLongitude(12.34);
        entityManager.persist(location);

        company = new Company();
        company.setName("Miau");
        company.setRuc("12345678");
        company.setEmail("miau@gmail.com");
        company.setPassword("12345678");
        company.setCreated(LocalDateTime.of(2020,1,1,1,1));
        company.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        company.setRole(Role.COMPANY);
        company.getLocations().add(location);

        entityManager.persist(company);
        entityManager.flush();
    }

    @Test
    void testCreation() {
        Company foundCompany = companyRepository.findById(this.company.getId()).orElse(null);
        assertNotNull(foundCompany);

        assertEquals("Miau", foundCompany.getName());
        assertEquals("12345678", foundCompany.getPassword());
        assertEquals("12345678", foundCompany.getRuc());
        assertEquals(Role.COMPANY, foundCompany.getRole());
        assertEquals("miau@gmail.com", foundCompany.getEmail());
        assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), foundCompany.getCreated());
        assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), foundCompany.getLastUpdated());
    }

    @Test
    void testLocations() {
        Company foundCompany = companyRepository.findById(this.company.getId()).orElse(null);
        assertNotNull(foundCompany);

        assertEquals(1, foundCompany.getLocations().size());
        assertEquals(location, foundCompany.getLocations().get(0));
    }
}
