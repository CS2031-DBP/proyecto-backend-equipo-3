package project.petpals.person.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import project.petpals.person.domain.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonDtoValidationTest {

    private Validator validator;

    @Test
    public void shouldFailSizeRestriction() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;

        PersonDto personDto = new PersonDto();
        personDto.setName("J");
        Set<ConstraintViolation<PersonDto>> violations = validator.validate(personDto);
        assertEquals(1, violations.size());
    }
}
