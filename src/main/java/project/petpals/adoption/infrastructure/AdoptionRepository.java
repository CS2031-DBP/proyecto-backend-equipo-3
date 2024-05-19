package project.petpals.adoption.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.domain.PetPersonId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, PetPersonId> {
    Page<Adoption> findAllByPersonId(Long personId, Pageable pageable);
}
