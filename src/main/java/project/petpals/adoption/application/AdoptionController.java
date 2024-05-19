package project.petpals.adoption.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.domain.AdoptionService;
import project.petpals.adoption.domain.PetPersonId;
import project.petpals.adoption.dtos.NewAdoptionDto;
import project.petpals.pet.dtos.NewPetDto;

@RestController
@RequestMapping("/adoptions")
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;

    @GetMapping("/{adoptionId}")
    public ResponseEntity<Adoption> getAdoption(@PathVariable PetPersonId adoptionId) {
        return ResponseEntity.ok(adoptionService.getAdoption(adoptionId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Adoption>> getAdoptionByUser(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(adoptionService.getAdoptionsByUser(page, size));
    }

    @PostMapping()
    public ResponseEntity<Void> saveAdoption(@RequestBody NewAdoptionDto newAdoptionDto) {
        adoptionService.saveAdoption(newAdoptionDto);
        return ResponseEntity.created(null).build();
    }
}
