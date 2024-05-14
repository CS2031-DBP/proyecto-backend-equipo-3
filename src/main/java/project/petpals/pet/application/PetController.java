package project.petpals.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.petpals.pet.domain.Pet;

public class PetController {
    //Pet @requestMapping("/pet")

    @Autowired
    private int petNo;

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPet(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
        petService.updatePet(id, pet);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<Void> updatePetLocation(
            @PathVariable Long id,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude
    ) {
        petService.updatePetLocation(id, latitude, longitude);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{species}")
    public ResponseEntity<Pet> getPetBySpecies(@PathVariable String species) {
        return ResponseEntity.ok(petService.getPetBySpecies(species));
    }

    @PostMapping()
    public ResponseEntity<Void> savePet(@RequestBody Pet pet) {
        petService.savePet(pet);
        return ResponseEntity.created(null).build();
    }

}
