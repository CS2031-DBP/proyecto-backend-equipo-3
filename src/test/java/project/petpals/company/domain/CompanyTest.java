package project.petpals.company.domain;

import project.petpals.location.domain.Location;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyTest {

    private Location location;
    private Company company;

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
    }

    @Test
    void testCreation() {
        assertEquals("UTEC", company.getName());
        assertEquals("12345678", company.getPassword());
        assertEquals("12345678", company.getRuc());
        assertEquals("utec@gmail.com", company.getEmail());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), company.getCreated());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), company.getLastUpdated());
        assertEquals("COMPANY", company.getRole().name());
    }

    @Test
    void testGetLocations() {
        assertEquals(location, company.getLocations().get(0));

    }
}
