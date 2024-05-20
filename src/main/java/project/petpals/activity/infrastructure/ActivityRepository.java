package project.petpals.activity.infrastructure;

import project.petpals.activity.domain.Activity;
import project.petpals.activity.domain.ActivityStatus;
import project.petpals.activity.domain.ActivityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Page<Activity> findAllByCompanyId(Long companyId, Pageable pageable);
    Page<Activity> findAllByActivityStatus(ActivityStatus activityStatus, Pageable pageable);
    Page<Activity> findAllByActivityType(ActivityType activityType, Pageable pageable);
    Page<Activity> findAllByStartDateGreaterThan(LocalDate startDate, Pageable pageable);

}
