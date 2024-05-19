package project.petpals.adoption.infrastructure;

import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.domain.PetPersonId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, PetPersonId> {
}
