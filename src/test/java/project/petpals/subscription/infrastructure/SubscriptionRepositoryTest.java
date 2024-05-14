package project.petpals.subscription.infrastructure;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import project.petpals.AbstractContainerBaseTest;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.subscription.domain.PersonCompanyId;
import project.petpals.subscription.domain.Status;
import project.petpals.subscription.domain.Subscription;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubscriptionRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SubscriptionRepository subsRepository;

    @Autowired
    TestEntityManager entityManager;

    private Person person;
    private Company company;
    private Subscription subscription;

    void setup() {
        person = new Person();
        person.setUsername("Gadiel Velarde");
        person.setPassword("123456");
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("a@utec.edu.pe");
        entityManager.persist(person);

        company = new Company();
        company.setUsername("Compania");
        company.setEmail("compania@gmail.com");
        company.setPassword("123454");
        company.setRuc("123456");
        company.setCreated(LocalDateTime.now());
        company.setLastUpdated(LocalDateTime.now());
        entityManager.persist(company);

        subscription = new Subscription();
        subscription.setSubscriptionDate(LocalDateTime.now());
        subscription.setStatus(Status.ACTIVE);
        subscription.setReceiveNotifs(true);
        subscription.setCompany(company);
        subscription.setPerson(person);

    }

    @Test
    void testCreationAndCustomId() {
        setup();
        subscription.setId(new PersonCompanyId(company.getId(), person.getId()));
        entityManager.persist(subscription);
        entityManager.flush();

        Optional<Subscription> retrievedSubscription = subsRepository.findById(subscription.getId());
        assertEquals(subscription, retrievedSubscription.get());
    }
}
