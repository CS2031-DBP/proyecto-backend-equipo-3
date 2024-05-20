package project.petpals.adoption.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.petpals.adoption.dtos.NewAdoptionDto;
import project.petpals.adoption.infrastructure.AdoptionRepository;
import project.petpals.auth.AuthUtils;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.ConflictException;
import project.petpals.exceptions.NotFoundException;
import project.petpals.exceptions.UnauthorizedAccessException;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.infrastructure.PetRepository;

import java.time.LocalDateTime;

@Service
public class AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AuthUtils authUtils;

    public void saveAdoption(NewAdoptionDto newAdoptionDto) {
        // get person from security context
        String email = authUtils.getCurrentUserEmail();

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found!"));


        PetPersonId adoptionId = new PetPersonId(newAdoptionDto.getPetId(), person.getId());
        if (adoptionRepository.findById(adoptionId).isPresent()) {
            throw new ConflictException("Adoption already exists!");
        }

        Pet pet = petRepository.findById(newAdoptionDto.getPetId()).orElseThrow(
                () -> new NotFoundException("Pet not found!"));

        Company company = companyRepository.findById(pet.getCompany().getId()).orElseThrow(
                () -> new NotFoundException("Company not found!"));


        Adoption adoption = new Adoption();
        adoption.setId(adoptionId);
        adoption.setPet(pet);
        adoption.setCompany(company);
        adoption.setPerson(person);
        adoption.setAdoptionDate(LocalDateTime.now());
        adoption.setDescription(newAdoptionDto.getDescription());
        adoptionRepository.save(adoption);
    }

    public Page<Adoption> getAdoptionsByUser(int page, int size) {
        // get person from security context
        String email = authUtils.getCurrentUserEmail();

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found!"));

        return adoptionRepository.findAllByPersonId(person.getId(), PageRequest.of(page, size));
    }

    public Adoption getAdoption(PetPersonId adoptionId) {
        Adoption found = adoptionRepository.findById(adoptionId).orElseThrow(
                () -> new NotFoundException("Adoption not found!"));

        if (!authUtils.isResourceOwner(found.getPerson().getId())) {
            throw new UnauthorizedAccessException("You have not adopted this pet.");
        }
        return found;
    }
}
