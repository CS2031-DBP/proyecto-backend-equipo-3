package project.petpals.location.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setAddress("Jr Medrano Silva");
        location.setLatitude(-40.5);
        location.setLongitude(12.34);
    }

    @Test
    void testCreation() {
        assertEquals("Jr Medrano Silva", location.getAddress());
        assertEquals(-40.5, location.getLatitude());
        assertEquals(12.34, location.getLongitude());
    }

}
