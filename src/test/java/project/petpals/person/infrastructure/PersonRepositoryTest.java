package project.petpals.person.infrastructure;

import project.petpals.AbstractContainerBaseTest;
import project.petpals.person.domain.Person;
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
public class PersonRepositoryTest extends AbstractContainerBaseTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    TestEntityManager entityManager;

    private Person person;

    @BeforeEach
    void setup() {
        person = new Person();
        person.setName("Julio");
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("julio@gmail.com");
        person.setPassword("12345678");
        person.setRole(Role.PERSON);
        entityManager.persist(person);
        entityManager.flush();
    }

    @Test
    public void testCreation() {
        Person retrievedPerson = personRepository.findById(person.getId()).orElse(null);
        assertNotNull(retrievedPerson);
        assertNotNull(retrievedPerson.getId());
        assertEquals(retrievedPerson.getName(), "Julio");
        assertEquals(retrievedPerson.getCreated(), LocalDateTime.of(2020, 1, 1, 1, 1));
        assertEquals(retrievedPerson.getLastUpdated(), LocalDateTime.of(2020, 1, 1, 1, 1));
        assertEquals(retrievedPerson.getEmail(), "julio@gmail.com");
    }

    @Test
    public void testFindByEmail() {
        Person retrievedPerson = personRepository.findByEmail(person.getEmail()).orElse(null);
        assertNotNull(retrievedPerson);
        assertNotNull(retrievedPerson.getId());
        assertEquals(retrievedPerson.getName(), "Julio");
        assertEquals(retrievedPerson.getCreated(), LocalDateTime.of(2020, 1, 1, 1, 1));
        assertEquals(retrievedPerson.getLastUpdated(), LocalDateTime.of(2020, 1, 1, 1, 1));
        assertEquals(retrievedPerson.getEmail(), "julio@gmail.com");
    }

}
