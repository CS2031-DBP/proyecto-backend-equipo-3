package project.petpals.subscription.domain;

import project.petpals.company.domain.Company;
import project.petpals.person.domain.Person;
import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubscriptionTest {

    private Subscription subscription;
    private Company company;
    private Person person;
    private PersonCompanyId subscriptionId;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setName("UTEC");
        company.setRuc("12345678");
        company.setEmail("utec@gmail.com");
        company.setPassword("12345678");
        company.setCreated(LocalDateTime.of(2020, 1, 1, 1, 1));
        company.setLastUpdated(LocalDateTime.of(2020, 1, 1, 1, 1));
        company.setRole(Role.COMPANY);

        person = new Person();
        person.setId(2L);
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("joaquin@gmail.com");
        person.setPassword("12345678");
        person.setName("Joaquin");
        person.setRole(Role.PERSON);

        subscriptionId = new PersonCompanyId(person.getId(), company.getId());

        subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setCompany(company);
        subscription.setPerson(person);
        subscription.setReceiveNotifs(true);
        subscription.setStatus(Status.ACTIVE);
        subscription.setSubscriptionDate(LocalDateTime.of(2020, 1, 1, 1, 1));
    }

    @Test
    void testCreation() {
        assertEquals(company, subscription.getCompany());
        assertEquals(person, subscription.getPerson());
        assertTrue(subscription.getReceiveNotifs());
        assertEquals(Status.ACTIVE, subscription.getStatus());
        assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), subscription.getSubscriptionDate());
        assertEquals(subscriptionId, subscription.getId());

    }

    @Test
    void testGetCompany() {
        assertEquals("UTEC", subscription.getCompany().getName());
    }

    @Test
    void testGetPerson() {
        assertEquals("Joaquin", subscription.getPerson().getName());
    }

}
