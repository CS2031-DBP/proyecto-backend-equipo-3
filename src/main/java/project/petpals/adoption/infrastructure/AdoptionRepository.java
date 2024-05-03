package project.petpals.adoption.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.adoption.domain.Adoption;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
}
