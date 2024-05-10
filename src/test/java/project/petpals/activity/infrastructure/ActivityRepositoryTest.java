package project.petpals.activity.infrastructure;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.petpals.AbstractContainerBaseTest;
import project.petpals.activity.domain.Activity;
import project.petpals.activity.domain.ActivityStatus;
import project.petpals.activity.domain.ActivityType;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ActivityRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    TestEntityManager entityManager;

    private Company company;
    private Activity activity;

    @BeforeEach
    void setup() {
        company = new Company();
        company.setUsername("Gatitos Felices");
        company.setEmail("gatitosfelices@gmail.com");
        company.setPassword("123456");
        company.setRuc("123456");
        company.setCreated(LocalDateTime.now());
        company.setLastUpdated(LocalDateTime.now());
        entityManager.persist(company);

        activity = new Activity();
        activity.setActivityType(ActivityType.WORKSHOP);
        activity.setActivityStatus(ActivityStatus.NOT_STARTED);
        activity.setCompany(company);
        activity.setName("Ciencia de gatos");
        activity.setStartDate(LocalDateTime.now());
        activity.setEndDate(activity.getStartDate().plusDays(20));
        activity.setAddress("Jr. Medrano Silva");
        entityManager.persist(activity);

        entityManager.flush();
    }

    @Test
    void testCreate() {
        Optional<Activity> retrievedActivity = activityRepository.findById(activity.getId());
        assertEquals(retrievedActivity.get(),activity);
    }

    @Test
    void testFindAllByType() {

        Pageable page = PageRequest.of(0,5);
        Page<Activity> res = activityRepository.findAllByActivityType(ActivityType.WORKSHOP, page);

        assertEquals(res.getTotalElements(),1);
        assertEquals(res.stream().findFirst().get(), activity);
        assertEquals(res.stream().findFirst().get().getCompany(), company);
    }

    @Test
    void testFindByStartDate() {

        Pageable page = PageRequest.of(0,5);
        Page<Activity> res = activityRepository.findAllByStartDateGreaterThan(LocalDateTime.of(2020,1,1,1,1), page);
        assertEquals(1,res.getTotalElements());
        assertEquals(activity, res.stream().findFirst().get());

    }

    @Test
    void testFindByStartDateAndReturnEmpty() {

        Pageable page = PageRequest.of(0,5);
        Page<Activity> res = activityRepository.findAllByStartDateGreaterThan(LocalDateTime.of(2025,1,1,1,1), page);
        assertTrue(res.isEmpty());

    }




}

/* WHAT SHOULD THE REPOSITORY DO?
 * - Create an activity and save all of its attributes
 * - Find all by type
 * - Find all by Date greater than
 * */