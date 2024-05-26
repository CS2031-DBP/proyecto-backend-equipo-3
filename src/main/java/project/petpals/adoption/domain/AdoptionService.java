package project.petpals.adoption.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.petpals.adoption.dtos.AdoptionResponseDto;
import project.petpals.adoption.dtos.NewAdoptionDto;
import project.petpals.adoption.event.AdoptionCreatedEvent;
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
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.dtos.PetDto;
import project.petpals.pet.infrastructure.PetRepository;

import java.time.LocalDate;


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
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;



    private AdoptionResponseDto convertToDTO(Adoption adoption) {
        AdoptionResponseDto res = modelMapper.map(adoption, AdoptionResponseDto.class);
        res.setPetDto(modelMapper.map(adoption.getPet(), PetDto.class));
        return res;
    }

    public void saveAdoption(NewAdoptionDto newAdoptionDto) {
        // get person from security context
        String email = authUtils.getCurrentUserEmail();

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found!"));


        PetPersonId adoptionId = new PetPersonId(newAdoptionDto.getPetId(), person.getId());
        if (adoptionRepository.findByPetId(newAdoptionDto.getPetId()).isPresent()) {
            throw new ConflictException("Adoption already exists!");
        }

        Pet pet = petRepository.findById(newAdoptionDto.getPetId()).orElseThrow(
                () -> new NotFoundException("Pet not found!"));

        Company company = companyRepository.findById(pet.getCompany().getId()).orElseThrow(
                () -> new NotFoundException("Company not found!"));
        pet.setPetStatus(PetStatus.ADOPTED);
        petRepository.save(pet);

        Adoption adoption = new Adoption();
        adoption.setId(adoptionId);
        adoption.setPet(pet);
        adoption.setCompany(company);
        adoption.setPerson(person);
        adoption.setAdoptionDate(LocalDate.now());
        adoption.setDescription(newAdoptionDto.getDescription());
        adoptionRepository.save(adoption);

        eventPublisher.publishEvent(new AdoptionCreatedEvent(adoption));
    }

    public Page<AdoptionResponseDto> getAdoptionsByUser(int page, int size) {
        // get person from security context
        String email = authUtils.getCurrentUserEmail();

        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found!"));

        return adoptionRepository.findAllByPersonId(person.getId(), PageRequest.of(page, size)
                ).map(this::convertToDTO);
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
