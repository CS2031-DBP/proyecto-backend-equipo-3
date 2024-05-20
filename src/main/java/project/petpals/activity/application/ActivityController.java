package project.petpals.activity.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.petpals.activity.domain.Activity;
import project.petpals.activity.domain.ActivityService;
import project.petpals.activity.domain.ActivityStatus;
import project.petpals.activity.domain.ActivityType;
import project.petpals.activity.dtos.ActivityResponseDto;
import project.petpals.activity.dtos.NewActivityDto;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PreAuthorize("hasRole('ROLE_PERSON') or hasRole('ROLE_COMPANY')")
    @GetMapping("type/{type}")
    public ResponseEntity<Page<ActivityResponseDto>> getActivityByType(
            @PathVariable ActivityType type, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(activityService.getActivityByType(type, page, size));
    }


    @PreAuthorize("hasRole('ROLE_PERSON') or hasRole('ROLE_COMPANY')")
    @GetMapping("date/{date}")
    public ResponseEntity<Page<ActivityResponseDto>> getActivitiesByDate(@PathVariable LocalDate date, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(activityService.getActivitiesByDate(date, page, size));
    }

    @PreAuthorize("hasRole('ROLE_PERSON') or hasRole('ROLE_COMPANY')")
    @GetMapping("company/{companyId}")
    public ResponseEntity<Page<ActivityResponseDto>> getActivitiesByCompany(@PathVariable Long companyId, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(activityService.getActivitiesByCompany(companyId, page, size));
    }

    @PreAuthorize("hasRole('ROLE_PERSON') or hasRole('ROLE_COMPANY')")
    @GetMapping("status/{status}")
    public ResponseEntity<Page<ActivityResponseDto>> getActivitiesByStatus(@PathVariable ActivityStatus status, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(activityService.getActivitiesByStatus(status, page, size));
    }

    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @PostMapping()
    public ResponseEntity<Void> saveActivity(@RequestBody NewActivityDto newActivityDto) {
        activityService.saveActivity(newActivityDto);
        return ResponseEntity.created(null).build();
    }

    // will updating activities be necessary?
//    @PatchMapping("/{id}")
//    public ResponseEntity<Void> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
//        activityService.updateActivity(id, activity);
//        return ResponseEntity.ok().build();
//    }

    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) throws AccessDeniedException {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }


}
