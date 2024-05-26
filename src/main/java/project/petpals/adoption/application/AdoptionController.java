package project.petpals.adoption.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.petpals.activity.domain.Activity;
import project.petpals.activity.dtos.ActivityResponseDto;
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.domain.AdoptionService;
import project.petpals.adoption.domain.PetPersonId;
import project.petpals.adoption.dtos.AdoptionResponseDto;
import project.petpals.adoption.dtos.NewAdoptionDto;
import project.petpals.adoption.infrastructure.EmailService;
import project.petpals.company.dtos.CompanyDto;
import project.petpals.pet.dtos.NewPetDto;

@RestController
@RequestMapping("/adoptions")
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;


    @GetMapping("/{petId}/{personId}")
    public ResponseEntity<Adoption> getAdoption(@PathVariable Long petId, @PathVariable Long personId) {
        PetPersonId adoptionId = new PetPersonId(petId, personId);
        return ResponseEntity.ok(adoptionService.getAdoption(adoptionId));
    }

    @GetMapping("/mine")
    public ResponseEntity<Page<AdoptionResponseDto>> getAdoptionByUser(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(adoptionService.getAdoptionsByUser(page, size));
    }

    @PostMapping()
    public ResponseEntity<Void> saveAdoption(@RequestBody NewAdoptionDto newAdoptionDto) {
        adoptionService.saveAdoption(newAdoptionDto);
        return ResponseEntity.created(null).build();
    }

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendEmail")
    public String sendTestEmail(@RequestParam String email) {
        String subject = "Test Email";
        String text = "This is a test email from PetPals.";
        emailService.sendSimpleMessage(email, subject, text);
        return "Email sent to " + email;
    }





}
