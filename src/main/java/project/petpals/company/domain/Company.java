package project.petpals.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.petpals.activity.domain.Activity;
import project.petpals.subscription.domain.Subscription;
import project.petpals.user.domain.User;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Company extends User {

    @Column(name = "ruc", nullable = false)
    private String ruc;

    @OneToMany
    private List<Subscription> subscriptions;

    @OneToMany
    private List<Activity> activities;
// mayhaps delete this??? ^
    // also create drop uwuwm
}
