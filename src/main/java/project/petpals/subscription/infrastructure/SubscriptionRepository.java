package project.petpals.subscription.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.subscription.domain.PersonCompanyId;
import project.petpals.subscription.domain.Subscription;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findById(PersonCompanyId personCompanyId);
}
