package project.petpals.adoption.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.petpals.adoption.domain.Adoption;

public class AdoptionController {

    @GetMapping("/{adoptionId}")
    public ResponseEntity<Adoption> getAdoption(@PathVariable Long adoptionId) {
        return ResponseEntity.ok(adoptionService.getAdoption(adoptionId));
    }

    @GetMapping("/user{userId}")
    public ResponseEntity<Adoption> getAdoptionByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adoptionService.getAdoptionByUser(userId));
    }

    @PostMapping()
    public ResponseEntity<Void> saveAdoption(@RequestBody Adoption adoption) {
        adoptionService.saveAdoption(adoption);
        return ResponseEntity.created(null).build();
    }
}
