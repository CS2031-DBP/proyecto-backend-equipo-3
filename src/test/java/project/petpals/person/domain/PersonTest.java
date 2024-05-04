package project.petpals.person.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    @Test
    void testPersonCreation() {
        Person person = new Person();
        person.setUsername("Arthur Barraza");
        person.setPassword("123456");
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("a@utec.edu.pe");

        assertEquals("Arthur Barraza", person.getUsername());
        assertEquals("123456", person.getPassword());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), person.getCreated());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), person.getLastUpdated());
        assertEquals("a@utec.edu.pe", person.getEmail());
    }
}
