package project.petpals.location.infrastructure;

import org.springframework.data.repository.CrudRepository;
import project.petpals.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
//    Optional<Location> findLocationByLongitudeAndLatitude(Double longitude, Double latitude);
}
