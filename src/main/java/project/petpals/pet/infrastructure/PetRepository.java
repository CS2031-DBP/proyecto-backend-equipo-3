package project.petpals.pet.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.Species;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findAllBySpecies(Species species, Pageable pageable);
    Page<Pet> findAllByBreed(String breed, Pageable pageable);
}
