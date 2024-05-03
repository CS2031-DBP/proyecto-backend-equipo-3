package project.petpals.pet.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.pet.domain.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
