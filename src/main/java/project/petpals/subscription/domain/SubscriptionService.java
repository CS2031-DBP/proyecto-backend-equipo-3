package project.petpals.subscription.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.petpals.adoption.domain.PetPersonId;
import project.petpals.auth.AuthUtils;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.ConflictException;
import project.petpals.exceptions.NotFoundException;
import project.petpals.exceptions.UnauthorizedAccessException;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.subscription.dtos.NewSubscriptionDto;
import project.petpals.subscription.infrastructure.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AuthUtils authUtils;

    public void saveSubscription(Long companyId, NewSubscriptionDto newSubscriptionDto) {
        // get user from current security context
        String email = authUtils.getCurrentUserEmail();

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found"));
        Company company = companyRepository.findById(companyId).orElseThrow(
                () -> new NotFoundException("Company not found"));

        Optional<Subscription> found = subscriptionRepository.findById(new PersonCompanyId(company.getId(),person.getId()));
        if (found.isPresent() && found.get().getStatus() == Status.ACTIVE) throw new ConflictException("Subscription already exists");

//        if (found.isPresent() && found.get().getStatus() == Status.CANCELLED) {
//            found.get().setStatus(Status.ACTIVE);
//            subscriptionRepository.save(found.get());
//            return;
//        }
        Subscription subscription = new Subscription();
        PersonCompanyId subscriptionId = new PersonCompanyId(company.getId(), person.getId());
        subscription.setId(subscriptionId);
        subscription.setPerson(person);
        subscription.setCompany(company);
        subscription.setStatus(Status.ACTIVE);
        subscription.setSubscriptionDate(LocalDateTime.now());
        subscription.setReceiveNotifs(newSubscriptionDto.getReceiveNotifs());
        subscriptionRepository.save(subscription);
    }

    public Page<Subscription> getSubscriptionByCompany(int page, int size) {
        // get user from current security context
        String email = authUtils.getCurrentUserEmail();

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Company not found"));

        return subscriptionRepository.findAllByCompanyId(company.getId(), PageRequest.of(page, size));
    }

    public List<Subscription> getSubscriptionByPerson() {

        // get user from current security context
        String email = authUtils.getCurrentUserEmail();

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found"));

        return subscriptionRepository.findAllByPersonId(person.getId());
    }

    public void deleteSubscription(Long companyId) {
        // get user from current security context
        String email = authUtils.getCurrentUserEmail();
        Person owner = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found"));

        Subscription subscription = subscriptionRepository.findById(new PersonCompanyId( companyId,owner.getId())).orElseThrow(
                () -> new NotFoundException("Subscription not found"));
//        if (!authUtils.isResourceOwner(subscription.getPerson().getId())) {
//            throw new UnauthorizedAccessException("You are not allowed to delete this subscription");
//        }

        subscription.setStatus(Status.CANCELLED);
        subscriptionRepository.save(subscription);

    }

    public Subscription getSubscription(Long companyId) {
        // get user from current security context
        String email = authUtils.getCurrentUserEmail();
        Person owner = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found"));

        Subscription found = subscriptionRepository.findById(new PersonCompanyId(companyId, owner.getId())).orElseThrow(
                () -> new NotFoundException("Subscription not found"));
        if (found.getStatus() == Status.CANCELLED) throw new NotFoundException("Subscription not found");
        return found;
    }
}
