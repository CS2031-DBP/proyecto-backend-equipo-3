package project.petpals.subscription.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_PERSON')")
    @PostMapping("/{companyId}")
    public ResponseEntity<Void> saveSubscription(
            @PathVariable Long companyId, @RequestBody NewSubscriptionDto newSubscriptionDto) {
        subscriptionService.saveSubscription(companyId, newSubscriptionDto);
        return ResponseEntity.created(null).build();
    }

    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @GetMapping("/company")
    public ResponseEntity<Page<Subscription>> getSubscriptionByCompany(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByCompany(page, size));
    }

    @PreAuthorize("hasRole('ROLE_PERSON')")
    @GetMapping("/person")
    public ResponseEntity<List<Subscription>> getSubscriptionByPerson() {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByPerson());
    }

    @PreAuthorize("hasRole('ROLE_PERSON')")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long companyId) {
        subscriptionService.deleteSubscription(companyId);
        return ResponseEntity.noContent().build();
    }
}
