package project.petpals.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.petpals.pet.domain.PetService;
import project.petpals.pet.domain.Species;
import project.petpals.pet.dtos.NewPetDto;
import project.petpals.pet.dtos.PetDto;
import project.petpals.pet.dtos.UpdatePetDto;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPet(id));
    }

    @GetMapping("/breed/{breed}")
    public ResponseEntity<Page<PetDto>> findAllByBreed(
            @PathVariable String breed,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(petService.findAllByBreed(breed, page, size));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Page<PetDto>> findAllByBreed(
            @PathVariable Long companyId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(petService.findAllInAdoptionByCompany(companyId, page, size));
    }

    @GetMapping("/inAdoption")
    public ResponseEntity<Page<PetDto>> findAllByStatus(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(petService.findAllInAdoption(page, size));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePet(@PathVariable Long id, @RequestBody UpdatePetDto updatePetDto) {
        petService.ownerUpdatePet(id, updatePetDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) throws AccessDeniedException {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/species/{species}")
    public ResponseEntity<Page<PetDto>> getPetBySpecies(@PathVariable Species species, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(petService.getPetBySpecies(species, page, size));
    }

    @PostMapping()
    public ResponseEntity<Void> savePet(@RequestBody NewPetDto newPetDto) {
        petService.savePet(newPetDto);
        return ResponseEntity.created(null).build();
    }
}
