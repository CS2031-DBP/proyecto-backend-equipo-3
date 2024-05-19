package project.petpals.subscription.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.petpals.subscription.domain.PersonCompanyId;
import project.petpals.subscription.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, PersonCompanyId> {
    Page<Subscription> findAllByCompanyId(Long companyId, Pageable pageable);
    List<Subscription> findAllByPersonId(Long personId);
}
