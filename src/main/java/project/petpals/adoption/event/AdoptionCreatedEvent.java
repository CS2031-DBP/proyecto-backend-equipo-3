package project.petpals.adoption.event;


import org.springframework.context.ApplicationEvent;
import project.petpals.adoption.domain.Adoption;

public class AdoptionCreatedEvent extends ApplicationEvent {
    private final Adoption adoption;

    public AdoptionCreatedEvent(Adoption adoption) {
        super(adoption);
        this.adoption = adoption;
    }

    public Adoption getAdoption() {
        return adoption;
    }
}