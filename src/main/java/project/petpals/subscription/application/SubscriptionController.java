package project.petpals.subscription.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.petpals.subscription.domain.PersonCompanyId;
import project.petpals.subscription.domain.Subscription;
import project.petpals.subscription.domain.SubscriptionService;
import project.petpals.subscription.dtos.NewSubscriptionDto;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping()
    public ResponseEntity<Void> saveSubscription(@RequestBody NewSubscriptionDto newSubscriptionDto) {
        subscriptionService.saveSubscription(newSubscriptionDto);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/company")
    public ResponseEntity<Page<Subscription>> getSubscriptionByCompany(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByCompany(page, size));
    }

    @GetMapping("/person/")
    public ResponseEntity<List<Subscription>> getSubscriptionByPerson() {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByPerson());
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable PersonCompanyId subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
        return ResponseEntity.noContent().build();
    }
}
