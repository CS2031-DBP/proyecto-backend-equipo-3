package project.petpals.subscription.infrastructure;

import project.petpals.AbstractContainerBaseTest;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.subscription.domain.PersonCompanyId;
import project.petpals.subscription.domain.Status;
import project.petpals.subscription.domain.Subscription;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubscriptionRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    TestEntityManager entityManager;

    private Company company;
    private Person person;
    private Subscription subscription;
    private PersonCompanyId subscriptionId;

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

        subscriptionId = new PersonCompanyId(person.getId(), company.getId());

        subscription = new Subscription();
        subscription.setPerson(person);
        subscription.setCompany(company);
        subscription.setSubscriptionDate(LocalDateTime.of(2020, 1, 1, 1, 1));
        subscription.setStatus(Status.ACTIVE);
        subscription.setReceiveNotifs(true);
        subscription.setId(subscriptionId);
        entityManager.persist(subscription);

        entityManager.flush();
    }

    @Test
    void testCreation() {
        Optional<Subscription> foundSubscription = subscriptionRepository.findById(subscriptionId);
        assertTrue(foundSubscription.isPresent());
        assertEquals(company, foundSubscription.get().getCompany());
        assertEquals(person, foundSubscription.get().getPerson());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), foundSubscription.get().getSubscriptionDate());
        assertEquals(Status.ACTIVE, foundSubscription.get().getStatus());
        assertTrue(foundSubscription.get().getReceiveNotifs());
        assertEquals(subscriptionId, foundSubscription.get().getId());
    }

    @Test
    void testStatus() {
        Optional<Subscription> foundSubscription = subscriptionRepository.findById(subscriptionId);
        assertTrue(foundSubscription.isPresent());
        foundSubscription.get().setStatus(Status.CANCELLED);
        entityManager.persist(foundSubscription.get());
        entityManager.flush();
        assertEquals(Status.CANCELLED, foundSubscription.get().getStatus());
    }

    @Test
    void DeleteByIdAndKeepPersonAndCompany() {
        subscriptionRepository.deleteById(subscriptionId);
        entityManager.flush();
        Optional<Subscription> foundSubscription = subscriptionRepository.findById(subscriptionId);
        assertTrue(foundSubscription.isEmpty());
        Optional<Company> foundCompany = companyRepository.findById(company.getId());
        assertTrue(foundCompany.isPresent());
        Optional<Person> foundPerson = personRepository.findById(person.getId());
        assertTrue(foundPerson.isPresent());
    }
}
