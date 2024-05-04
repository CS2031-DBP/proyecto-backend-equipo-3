package project.petpals.activity.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.petpals.company.domain.Company;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityTest {

    private Activity activity;
    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setUsername("UTEC");
        company.setEmail("utec@gmail.com");
        company.setPassword("123456");
        company.setRuc("kdajhflaksdj");
        company.setCreated(LocalDateTime.of(2020,1,1,3,3));
        company.setLastUpdated(LocalDateTime.of(2020,1,1,3,3));

        activity = new Activity();
        activity.setCompany(company);
        activity.setActivityStatus(ActivityStatus.IN_PROGRESS);
        activity.setName("Ciencia de gatos");
        activity.setStartDate(LocalDateTime.of(2020,1,1,3,3));
        activity.setEndDate(LocalDateTime.of(2020,3,1,3,3));
        activity.setAddress("Jr Medrano Silva");
        activity.setActivityType(ActivityType.WORKSHOP);
    }

    @Test
    void testActivity() {
        assertEquals(company, activity.getCompany());
        assertEquals(ActivityStatus.IN_PROGRESS, activity.getActivityStatus());
        assertEquals("Ciencia de gatos", activity.getName());
        assertEquals(LocalDateTime.of(2020,1,1,3,3), activity.getStartDate());
        assertEquals(LocalDateTime.of(2020,3,1,3,3), activity.getEndDate());
        assertEquals("Jr Medrano Silva", activity.getAddress());
        assertEquals(ActivityType.WORKSHOP, activity.getActivityType());
    }
}
