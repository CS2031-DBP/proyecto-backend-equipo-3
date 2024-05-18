package project.petpals.person.domain;

import project.petpals.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    private Person person;

    @BeforeEach
    void setup() {
        person = new Person();
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("joaquin@gmail.com");
        person.setPassword("12345678");
        person.setName("Joaquin");
        person.setRole(Role.PERSON);
    }

    @Test
    void testCreation() {
        assertEquals(LocalDateTime.of(2020,1,1,1,1), person.getCreated());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), person.getLastUpdated());
        assertEquals("Joaquin", person.getName());
        assertEquals("joaquin@gmail.com", person.getEmail());
        assertEquals("12345678", person.getPassword());
        assertEquals(Role.PERSON, person.getRole());
    }
}
