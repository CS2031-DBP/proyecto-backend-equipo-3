package project.petpals.subscription.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.subscription.domain.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
