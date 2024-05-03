package project.petpals.subscription.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class PersonCompanyId implements Serializable {

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "person_id")
    private Long personId;

    public PersonCompanyId() {}

    public PersonCompanyId(Long companyId, Long personId) {
        this.companyId = companyId;
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PersonCompanyId that = (PersonCompanyId) o;

        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, personId);
    }

}
