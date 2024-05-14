package project.petpals.activity.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.activity.domain.Activity;
import project.petpals.activity.domain.ActivityStatus;
import project.petpals.activity.domain.ActivityType;

import java.time.LocalDateTime;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Page<Activity> findAllByActivityType(ActivityType activityType, Pageable pageable);
    Page<Activity> findAllByStartDateGreaterThan(LocalDateTime localDateTime, Pageable page);

    // Para hallar las actividades IN PROGRESS
    Page<Activity> findAllByActivityStatus(ActivityStatus activityStatus, Pageable page);
}
