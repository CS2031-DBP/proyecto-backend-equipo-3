package project.petpals.subscription.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.petpals.company.domain.Company;
import project.petpals.person.domain.Person;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubscriptionTest {

    private Person person;
    private Subscription subscription;
    private Company company;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setUsername("Pan Cito");
        person.setPassword("123456");
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("pan.cito@utec.edu.pe");

        company = new Company();
        company.setUsername("UTEC");
        company.setEmail("utec@gmail.com");
        company.setPassword("123456");
        company.setRuc("kdajhflaksdj");
        company.setCreated(LocalDateTime.of(2020,1,1,3,3));
        company.setLastUpdated(LocalDateTime.of(2020,1,1,3,3));

        subscription = new Subscription();
        subscription.setCompany(company);
        subscription.setPerson(person);
        subscription.setSubscriptionDate(LocalDateTime.of(2020,1,1,2,2));
        subscription.setStatus(Status.ACTIVE);
        subscription.setReceiveNotifs(false);

    }

    @Test
    void testSubscription() {
        assertEquals(company, subscription.getCompany());
        assertEquals(person, subscription.getPerson());
        assertEquals(LocalDateTime.of(2020,1,1,2,2), subscription.getSubscriptionDate());
        assertEquals(Status.ACTIVE, subscription.getStatus());
        assertFalse(subscription.isReceiveNotifs());
    }
}
