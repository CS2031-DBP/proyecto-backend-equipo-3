package project.petpals.person.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.petpals.user.domain.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person extends User {

}
