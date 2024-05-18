package project.petpals.subscription.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.petpals.company.domain.Company;
import project.petpals.person.domain.Person;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Subscription {

    @EmbeddedId
    private PersonCompanyId id;

    @Column(name = "subscription_date", nullable = false)
    private LocalDateTime subscriptionDate;

    @Column(name = "receive_notifs", nullable = false)
    private Boolean receiveNotifs;

    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("companyId")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    private Person person;
}


