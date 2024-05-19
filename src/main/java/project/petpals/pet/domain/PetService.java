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
import project.petpals.pet.dtos.PetDto;
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
    private CompanyRepository companyRepository;;

    public PetDto getPet(Long petId) {
        // verify person is OWNER Of the pet
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new NotFoundException("Pet with id " + petId + " not found")
        );
        return modelMapper.map(pet, PetDto.class);
    }

    // rename to FindAllBySpecies
    // change to PetDto
    public Page<PetDto> getPetBySpecies(Species species, int page, int size) {
        Page<Pet> found =  petRepository.findAllBySpeciesAndPetStatus(species, PetStatus.IN_ADOPTION, PageRequest.of(page, size));
        return found.map(pet -> modelMapper.map(pet, PetDto.class));
    }

    public Page<PetDto> findAllByBreed(String breed, int page, int size) {
        Page<Pet> found =  petRepository.findALlByBreedIgnoreCaseAndPetStatus(breed, PetStatus.IN_ADOPTION, PageRequest.of(page, size));
        return found.map(pet -> modelMapper.map(pet, PetDto.class));

    }

    public Page<PetDto> findAllInAdoptionByCompany(Long companyId, int page, int size) {
        Page<Pet> found = petRepository.findAllByCompanyIdAndPetStatus(companyId, PetStatus.IN_ADOPTION, PageRequest.of(page, size));
        return found.map(pet -> modelMapper.map(pet, PetDto.class));
    }

    public Page<PetDto> findAllInAdoption(int page, int size) {
        Page<Pet> found =  petRepository.findAllByPetStatus(PetStatus.IN_ADOPTION, PageRequest.of(page, size));
        return found.map(pet -> modelMapper.map(pet, PetDto.class));
    }

    public void savePet(NewPetDto newPetDto) {
        // get company from current context
        Company company = companyRepository.findById(newPetDto.getCompanyId()).orElseThrow(
                ()->new NotFoundException("Company with id " + newPetDto.getCompanyId() + " not found")
        );
        Pet newPet = modelMapper.map(newPetDto, Pet.class);
        newPet.setPetStatus(PetStatus.IN_ADOPTION);
        newPet.setCompany(company);
        newPet.setDescription(newPetDto.getDescription());
    }

    public void deletePet(Long petId) throws AccessDeniedException {
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

    public void ownerUpdatePet(Long petId, UpdatePetDto updatePetDto) {

        // Change to get Person from current context and verify ownership
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new NotFoundException("Pet with id " + petId + " not found"));

        pet.setWeight(updatePetDto.getWeight());
        pet.setDescription(updatePetDto.getDescription());
        petRepository.save(pet);
    }
}
