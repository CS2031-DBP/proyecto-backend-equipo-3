package project.petpals.subscription.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.petpals.company.domain.Company;
import project.petpals.person.domain.Person;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
// property default "@id" dice
public class Subscription {

    @EmbeddedId
    private PersonCompanyId id;

    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    @Column(name = "receive_notifs")
    private boolean receiveNotifs;

    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("companyId")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    private Person person;
}
