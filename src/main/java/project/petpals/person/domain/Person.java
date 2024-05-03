package project.petpals.person.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import project.petpals.adoption.domain.Adoption;
import project.petpals.subscription.domain.Subscription;
import project.petpals.user.domain.User;

import java.util.List;

@Data
@Entity
public class Person extends User {
    @OneToMany
    private List<Subscription> subscriptions;

    @OneToMany
    private List<Adoption> adoptions;
}
