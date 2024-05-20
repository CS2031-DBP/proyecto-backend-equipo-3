package project.petpals.person.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.petpals.auth.AuthUtils;
import project.petpals.exceptions.NotFoundException;
import project.petpals.person.dtos.PersonDto;
import project.petpals.person.dtos.PersonSelfResponseDto;
import project.petpals.person.dtos.PersonSelfUpdateDto;
import project.petpals.person.infrastructure.PersonRepository;

import java.time.LocalDateTime;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthUtils authUtils;

    public PersonSelfResponseDto getSelf() {
        // find current user email
        String email = authUtils.getCurrentUserEmail();

        Person found =  personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found")
        );

        return modelMapper.map(found, PersonSelfResponseDto.class);
    }

    public PersonDto getPerson(Long id) {
        Person found = personRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Person not found")
        );

        return modelMapper.map(found, PersonDto.class);
    }

    public void updatePerson(PersonSelfUpdateDto personSelfUpdateDto) {
        // find current user email
        String email = authUtils.getCurrentUserEmail();

        Person found =  personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found")
        );

        // update found person
        found.setPassword(personSelfUpdateDto.getPassword());
        found.setName(personSelfUpdateDto.getName());
        found.setLastUpdated(LocalDateTime.now());
        personRepository.save(found);
    }
}
