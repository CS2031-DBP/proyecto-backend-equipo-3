package project.petpals.adoption.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PetPersonId implements Serializable {
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "person_id")
    private Long personId;

    public PetPersonId() {}

    public PetPersonId(Long petId, Long personId) {
        this.petId = petId;
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PetPersonId that = (PetPersonId) o;

        return Objects.equals(petId, that.petId) &&
                Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, personId);
    }

}
