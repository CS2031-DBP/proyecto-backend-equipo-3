package project.petpals.location.infrastructure;

import project.petpals.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findLocationByLongitudeAndLatitude(Double longitude, Double latitude);
}
