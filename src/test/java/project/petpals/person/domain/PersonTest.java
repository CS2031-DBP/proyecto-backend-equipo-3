package project.petpals.person.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.petpals.AbstractContainerBaseTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonTest extends AbstractContainerBaseTest {

    @Test
    void testPersonCreation() {
        Person person = new Person();
        person.setUsername("Gadiel Velarde");
        person.setPassword("123456");
        person.setCreated(LocalDateTime.of(2020,1,1,1,1));
        person.setLastUpdated(LocalDateTime.of(2020,1,1,1,1));
        person.setEmail("a@utec.edu.pe");

        assertEquals("Gadiel Velarde", person.getUsername());
        assertEquals("123456", person.getPassword());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), person.getCreated());
        assertEquals(LocalDateTime.of(2020,1,1,1,1), person.getLastUpdated());
        assertEquals("a@utec.edu.pe", person.getEmail());
    }
}
