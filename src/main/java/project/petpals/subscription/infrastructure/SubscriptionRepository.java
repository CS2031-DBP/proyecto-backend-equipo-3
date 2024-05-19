package project.petpals.subscription.infrastructure;

import project.petpals.subscription.domain.PersonCompanyId;
import project.petpals.subscription.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, PersonCompanyId> {

}
