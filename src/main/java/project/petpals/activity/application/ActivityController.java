package project.petpals.activity.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.petpals.activity.domain.Activity;
import project.petpals.activity.domain.ActivityService;
import project.petpals.activity.domain.ActivityStatus;
import project.petpals.activity.domain.ActivityType;
import project.petpals.activity.dtos.NewActivityDto;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/{type}")
    public ResponseEntity<Page<Activity>> getActivityByType(
            @PathVariable ActivityType type, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(activityService.getActivityByType(type, page, size));
    }


    @GetMapping("/{date}")
    public ResponseEntity<Page<Activity>> getActivitiesByDate(@PathVariable LocalDateTime date, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(activityService.getActivitiesByDate(date, page, size));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Page<Activity>> getActivitiesByCompany(@PathVariable Long companyId, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(activityService.getActivitiesByCompany(companyId, page, size));
    }

    @GetMapping("/{status}")
    public ResponseEntity<Page<Activity>> getActivitiesByStatus(@PathVariable ActivityStatus status, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(activityService.getActivitiesByStatus(status, page, size));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) throws AccessDeniedException {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }


}
