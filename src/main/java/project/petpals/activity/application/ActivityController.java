package project.petpals.activity.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.petpals.activity.domain.Activity;

import java.util.Date;

public class ActivityController {


    @GetMapping("/{type}")
    public ResponseEntity<Activity> getActivityByType(@PathVariable String type) {
        return ResponseEntity.ok(activityService.getActivityByType(type));
    }


    @GetMapping("/{date}")
    public ResponseEntity<Activity> getActivitiesByDate(@PathVariable Date date) {
        return ResponseEntity.ok(activityService.getActivitiesByDate(date));
    }

    @PostMapping()
    public ResponseEntity<Void> saveActivity(@RequestBody Activity activity) {
        activityService.saveActivity(activity);
        return ResponseEntity.created(null).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        activityService.updateActivity(id, activity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }


}
