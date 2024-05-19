package project.petpals.pet.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.petpals.pet.infrastructure.PetRepository;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;


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

    void savePet(Pet newPetDto) {

    }
}
