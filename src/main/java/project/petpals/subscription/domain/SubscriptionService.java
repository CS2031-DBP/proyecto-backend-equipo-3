package project.petpals.subscription.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.petpals.adoption.domain.PetPersonId;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.NotFoundException;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.subscription.infrastructure.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    private PersonRepository personRepository;
    private CompanyRepository companyRepository;

    public void saveSubscription(NewSubscriptionDto newSubscriptionDto) {
        // get user from current security context
        String email = "email";

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found"));
        Company company = companyRepository.findById(newSubscriptionDto.getCompanyId()).orElseThrow(
                () -> new NotFoundException("Company not found"));

        Subscription subscription = new Subscription();
        PersonCompanyId subscriptionId = new PersonCompanyId(person.getId(), company.getId());
        subscription.setId(subscriptionId);
        subscription.setPerson(person);
        subscription.setCompany(company);
        subscription.setStatus(Status.ACTIVE);
        subscription.setSubscriptionDate(LocalDateTime.now());
        subscription.setReceiveNotifs(newSubscriptionDto.getReceiveNotifs());
    }

    public Page<Subscription> getSubscriptionByCompany(Long companyId, int page, int size) {
        return subscriptionRepository.findAllByCompanyId(companyId, PageRequest.of(page, size));
    }

    public List<Subscription> getSubscriptionByPerson() {

        // get user from current security context
        String email = "email";

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found"));

        return subscriptionRepository.findAllByPersonId(person.getId());
    }

    public void deleteSubscription(PersonCompanyId subscriptionId) {
        // get user from current security context
        String email = "email";

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found"));
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(
                () -> new NotFoundException("Subscription not found"));
        if (!Objects.equals(subscription.getPerson().getId(), person.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete this subscription");
        }

        subscriptionRepository.deleteById(subscriptionId);

    }
}
