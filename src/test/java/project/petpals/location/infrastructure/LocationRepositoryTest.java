package project.petpals.location.infrastructure;

import project.petpals.AbstractContainerBaseTest;
import project.petpals.location.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class    LocationRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    TestEntityManager entityManager;

    private Location location;

    @BeforeEach
    void setup() {
        location = new Location();
        location.setLatitude(1.0);
        location.setLongitude(-1.0);
        location.setAddress("Test Address");
        entityManager.persist(location);
        entityManager.flush();

    }

        @Test
        public void testCreation() {
            Location retrievedLocation = locationRepository.findById(location.getId()).orElse(null);
            assertNotNull(retrievedLocation);
            assertNotNull(retrievedLocation.getId());
            assertEquals(retrievedLocation.getLatitude(), 1.0);
            assertEquals(retrievedLocation.getLongitude(), -1.0);
            assertEquals(retrievedLocation.getAddress(), "Test Address");
        }

        @Test
        public void testFindAllLocations() {
            Location location1 = new Location();
            location1.setLatitude(1.0);
            location1.setLongitude(1.0);
            location1.setAddress("Test Address 1");
            locationRepository.save(location1);
            Location location2 = new Location();
            location2.setLatitude(2.0);
            location2.setLongitude(2.0);
            location2.setAddress("Test Address 2");
            locationRepository.save(location2);

            assertEquals(locationRepository.count(), 3);
        }
}
