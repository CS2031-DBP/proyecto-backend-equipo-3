package project.petpals.pet.infrastructure;

import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findAllByCompanyId(Long companyId, Pageable pageable);
    Page<Pet> findAllByPetStatus(PetStatus petStatus, Pageable pageable);
    Page<Pet> findALlByBreed(String breed, Pageable pageable);
    Page<Pet> findAllBySpecies(Species species, Pageable pageable);

}
