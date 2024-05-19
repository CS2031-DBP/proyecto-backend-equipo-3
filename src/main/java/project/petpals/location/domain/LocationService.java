package project.petpals.location.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.petpals.exceptions.NotFoundException;
import project.petpals.location.infrastructure.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Location with id " + id + " not found"));
    }

}
