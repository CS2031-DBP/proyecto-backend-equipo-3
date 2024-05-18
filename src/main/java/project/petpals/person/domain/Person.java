package project.petpals.person.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.petpals.adoption.domain.Adoption;
import project.petpals.subscription.domain.Subscription;
import project.petpals.user.domain.User;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Person extends User {
    @OneToMany
    private List<Subscription> subscriptions;

    @OneToMany
    private List<Adoption> adoptions;
}
