package project.petpals.person.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.petpals.person.domain.Person;
import project.petpals.person.domain.PersonService;
import project.petpals.person.dtos.PersonDto;
import project.petpals.person.dtos.PersonSelfResponseDto;
import project.petpals.person.dtos.PersonSelfUpdateDto;
import project.petpals.user.dtos.ProfilePhoto;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PreAuthorize("hasRole('ROLE_PERSON') or hasRole('ROLE_COMPANY')")
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPerson(id));
    }

    @GetMapping("/me")
    public ResponseEntity<PersonSelfResponseDto> getSelf() {
        return ResponseEntity.ok(personService.getSelf());
    }


    @PatchMapping()
    public ResponseEntity<Void> updateSelf(@RequestBody PersonSelfUpdateDto personSelfUpdateDto) {
        personService.updatePerson(personSelfUpdateDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me/photo")
    public ResponseEntity<Void> updateProfilePhoto(@RequestBody ProfilePhoto profilePhoto) {
        personService.updateProfilePhoto(profilePhoto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me/banner")
    public ResponseEntity<Void> updateBannerPhoto(@RequestBody ProfilePhoto bannerPhoto) {
        personService.updateBannerPhoto(bannerPhoto);
        return ResponseEntity.ok().build();
    }
}
