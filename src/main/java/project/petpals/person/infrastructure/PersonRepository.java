package project.petpals.person.infrastructure;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import project.petpals.person.domain.Person;
import project.petpals.user.infrastructure.BaseUserRepository;

@Repository
//@Transactional
public interface PersonRepository extends BaseUserRepository<Person> {
}
