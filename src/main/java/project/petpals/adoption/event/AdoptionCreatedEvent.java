package project.petpals.adoption.event;


import project.petpals.adoption.domain.Adoption;

public class AdoptionCreatedEvent {
    private final Adoption adoption;

    public AdoptionCreatedEvent(Adoption adoption) {
        this.adoption = adoption;
    }

    public Adoption getAdoption() {
        return adoption;
    }
}