package project.petpals.activity.infrastructure;


import project.petpals.AbstractContainerBaseTest;
import project.petpals.activity.domain.Activity;
import project.petpals.activity.domain.ActivityStatus;
import project.petpals.activity.domain.ActivityType;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.location.domain.Location;
import project.petpals.location.infrastructure.LocationRepository;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ActivityRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    TestEntityManager entityManager;

    private Location location;
    private Company company;
    private Activity activity;
    @BeforeEach
    void setUp() {
        location = new Location();
        location.setAddress("Jr Medrano Silva");
        location.setLatitude(-40.5);
        location.setLongitude(12.34);
        entityManager.persist(location);

        company = new Company();
        company.setName("UTEC");
        company.setRuc("12345678");
        company.setEmail("utec@gmail.com");
        company.setPassword("12345678");
        company.setCreated(LocalDateTime.of(2020,1,1,1,1));
        company.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        company.setRole(Role.COMPANY);
        company.getLocations().add(location);
        entityManager.persist(company);

        activity = new Activity();
        activity.setActivityStatus(ActivityStatus.IN_PROGRESS);
        activity.setActivityType(ActivityType.WORKSHOP);
        activity.setName("Ciencia de Gatos");
        activity.setCompany(company);
        activity.getLocations().add(location);
        activity.setStartDate(LocalDateTime.of(2020,1,1,1,1));
        activity.setEndDate(LocalDateTime.of(2020,1,1,1,1));
        entityManager.persist(activity);

        entityManager.flush();
    }

    @Test
    void testCreation() {
        Activity foundActivity = activityRepository.findById(this.activity.getId()).orElse(null);
        assertNotNull(foundActivity);

        assertEquals(ActivityStatus.IN_PROGRESS, foundActivity.getActivityStatus());
        assertEquals(ActivityType.WORKSHOP, foundActivity.getActivityType());
        assertEquals("Ciencia de Gatos", foundActivity.getName());
        assertEquals(company, foundActivity.getCompany());
        assertEquals(location, foundActivity.getLocations().get(0));
        assertEquals(LocalDateTime.of(2020,1,1,1,1), foundActivity.getStartDate());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), foundActivity.getEndDate());
    }

    @Test
    void testLocationsAreEqual() {
        Activity foundActivity = activityRepository.findById(this.activity.getId()).orElse(null);
        assertNotNull(foundActivity);

        assertEquals(1, foundActivity.getLocations().size());
        assertEquals(location, foundActivity.getLocations().get(0));
        assertEquals(foundActivity.getLocations().get(0), foundActivity.getCompany().getLocations().get(0));
    }

    @Test
    void testFindAllByActivityStatus() {
        Page<Activity> foundActivities = activityRepository.findAllByActivityStatus(ActivityStatus.IN_PROGRESS, PageRequest.of(0,5));
        assertNotNull(foundActivities);
        assertEquals(activity, foundActivities.getContent().get(0));
    }

    @Test
    void testFindAllByActivityType() {
        Page<Activity> foundActivities = activityRepository.findAllByActivityType(ActivityType.WORKSHOP, PageRequest.of(0,5));
        assertNotNull(foundActivities);
        assertEquals(activity, foundActivities.getContent().get(0));
    }

    @Test
    void testFindAllByStartDateGreaterThan() {
        LocalDateTime date1 = LocalDateTime.of(2019,11,1,1,0);
        LocalDateTime date2 = LocalDateTime.of(2024,1,1,1,1);
        Page<Activity> activities1 = activityRepository.findAllByStartDateGreaterThan(date1, PageRequest.of(0,5));
        Page<Activity> activities2 = activityRepository.findAllByStartDateGreaterThan(date2, PageRequest.of(0,5));

        assertEquals(1, activities1.getContent().size());
        assertEquals(0, activities2.getContent().size());
    }

    @Test
    void testDeleteActivityByIdAndKeepCompanyAndLocation() {
        activityRepository.deleteById(this.activity.getId());
        entityManager.flush();
        entityManager.clear();

        Activity foundActivity = activityRepository.findById(this.activity.getId()).orElse(null);
        assertNull(foundActivity);
        Company foundCompany = companyRepository.findById(this.company.getId()).orElse(null);
        assertNotNull(foundCompany);
        Location foundLocation = locationRepository.findById(this.location.getId()).orElse(null);
        assertNotNull(foundLocation);
    }
}
