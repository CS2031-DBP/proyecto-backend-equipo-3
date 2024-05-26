package project.petpals.adoption.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.infrastructure.EmailService;
import project.petpals.person.domain.Person;

@Component
public class AdoptionEventListener {

    @Autowired
    private EmailService emailService;

    @EventListener
    public void handleAdoptionCreatedEvent(AdoptionCreatedEvent event) {
        Adoption adoption = event.getAdoption();
        Person person = adoption.getPerson();
        String subject = "Gracias por adoptar a una mascota";
        String text = "Querido " + person.getName() + ",\n\nGracias por adoptar a " + adoption.getPet().getName() + ".\n\nSaludos,\nPetPals";
        emailService.sendSimpleMessage(person.getEmail(), subject, text);
    }
}
