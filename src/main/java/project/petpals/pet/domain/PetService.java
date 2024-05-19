package project.petpals.pet.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.petpals.company.domain.Company;
import project.petpals.company.domain.CompanyService;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.NotFoundException;
import project.petpals.pet.dtos.NewPetDto;
import project.petpals.pet.dtos.UpdatePetDto;
import project.petpals.pet.infrastructure.PetRepository;

import java.nio.file.AccessDeniedException;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;


    // rename to FindAllBySpecies
    // change to PetDto
    Page<Pet> getPetBySpecies(Species species, int page, int size) {
        return petRepository.findAllBySpeciesAndPetStatus(species, PetStatus.IN_ADOPTION, PageRequest.of(page, size));
    }

    Page<Pet> findAllByBreed(String breed, int page, int size) {
        return petRepository.findALlByBreedIgnoreCaseAndPetStatus(breed, PetStatus.IN_ADOPTION, PageRequest.of(page, size));
    }

    Page<Pet> findAllInAdoptionByCompany(Long companyId, int page, int size) {
        return petRepository.findAllByCompanyIdAndPetStatus(companyId, PetStatus.IN_ADOPTION, PageRequest.of(page, size));
    }

    Page<Pet> findALlByStatus(PetStatus status, int page, int size) {
        return petRepository.findAllByPetStatus(status, PageRequest.of(page, size));
    }

    void savePet(NewPetDto newPetDto) {
        // get company from current context
        Company company = companyRepository.findById(newPetDto.getCompanyId()).orElseThrow(
                ()->new NotFoundException("Company with id " + newPetDto.getCompanyId() + " not found")
        );
        Pet newPet = modelMapper.map(newPetDto, Pet.class);
        newPet.setPetStatus(PetStatus.IN_ADOPTION);
    }

    void deletePet(Long petId) throws AccessDeniedException {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new NotFoundException("Pet with id " + petId + " not found"));
        Long companyId = pet.getCompany().getId();

        // check if the pet is in adoption
        // check if the company actually OWNS the pet
        if (pet.getPetStatus() != PetStatus.IN_ADOPTION) {
            throw new AccessDeniedException("Cannot perform delete pet operation.");
        }

        petRepository.deleteById(petId);

    }

    void ownerUpdatePet(Long petId, UpdatePetDto updatePetDto) {

        // Change to get Person from current context
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new NotFoundException("Pet with id " + petId + " not found"));

        pet.setWeight(updatePetDto.getWeight());
        pet.setDescription(updatePetDto.getDescription());
        petRepository.save(pet);
    }
}
