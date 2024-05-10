package project.petpals.adoption.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.domain.PetPersonId;

import java.util.Optional;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    Optional<Adoption> findById(PetPersonId petPersonId);
}
