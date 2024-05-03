package project.petpals.activity.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.activity.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
