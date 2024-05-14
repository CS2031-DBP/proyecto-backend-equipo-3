package project.petpals.person.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.petpals.person.domain.Person;

public class PersonController {
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPerson(id));
    }

    @GetMapping("/{id}?name={name}")
    public ResponseEntity<Person> getPersonByName(@PathVariable Long id, @PathVariable String name) {
        return ResponseEntity.ok(personService.getPersonByName(id, name));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        personService.updatePerson(id, person);
        return ResponseEntity.ok().build();
    }
}
