package project.petpals.person.infrastructure;

import project.petpals.person.domain.Person;
import project.petpals.user.infrastructure.UserRepository;

public interface PersonRepository extends UserRepository<Person> {
}
