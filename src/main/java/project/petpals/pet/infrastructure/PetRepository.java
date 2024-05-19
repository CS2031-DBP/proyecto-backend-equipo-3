package project.petpals.pet.infrastructure;

import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findAllByCompanyIdAndPetStatus(Long companyId, PetStatus status, Pageable pageable);
    Page<Pet> findAllByPetStatus(PetStatus petStatus, Pageable pageable);
    Page<Pet> findALlByBreedIgnoreCaseAndPetStatus(String breed, PetStatus status, Pageable pageable);
    Page<Pet> findAllBySpeciesAndPetStatus(Species species, PetStatus status, Pageable pageable);

}
