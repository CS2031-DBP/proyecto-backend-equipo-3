package project.petpals.activity.domain;

import project.petpals.company.domain.Company;
import project.petpals.location.domain.Location;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityTest {

    private Location location;
    private Company company;
    private Activity activity;
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

        activity = new Activity();
        activity.setActivityStatus(ActivityStatus.IN_PROGRESS);
        activity.setActivityType(ActivityType.WORKSHOP);
        activity.setName("Ciencia de Gatos");
        activity.setCompany(company);
        activity.getLocations().add(location);
        activity.setStartDate(LocalDateTime.of(2020,1,1,1,1));
        activity.setEndDate(LocalDateTime.of(2020,1,1,1,1));
    }

    @Test
    void testCreation() {
        assertEquals(ActivityStatus.IN_PROGRESS, activity.getActivityStatus());
        assertEquals(ActivityType.WORKSHOP, activity.getActivityType());
        assertEquals("Ciencia de Gatos", activity.getName());
        assertEquals(company, activity.getCompany());
        assertEquals(location, activity.getLocations().get(0));
        assertEquals(LocalDateTime.of(2020,1,1,1,1), activity.getStartDate());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), activity.getEndDate());

    }
}
