package project.petpals.subscription.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.petpals.subscription.domain.Subscription;

public class SubscriptionController {

    @PostMapping()
    public ResponseEntity<Void> saveSubscription(@RequestBody Subscription subscription) {
        subscriptionService.saveSubscription(subscription);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Subscription> getSubscriptionByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByCompany(companyId));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<Subscription> getSubscriptionByPerson(@PathVariable Long personId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByPerson(personId));
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
        return ResponseEntity.noContent().build();
    }
}
