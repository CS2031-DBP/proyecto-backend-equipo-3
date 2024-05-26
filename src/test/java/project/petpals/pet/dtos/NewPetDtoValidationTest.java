package project.petpals.pet.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.Validator;
import project.petpals.pet.domain.Species;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NewPetDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }

    @Test
    void shouldFailWhenAttributesAreNull() {
        NewPetDto newPetDto = new NewPetDto();
        var violations = validator.validate(newPetDto);
        assertEquals(7, violations.size());
    }

    @Test
    void shouldFailWhenAttributesViolateConstraints() {
        NewPetDto newPetDto = new NewPetDto();
        newPetDto.setName("J");
        newPetDto.setSex("male");
        newPetDto.setBreed("Poodle");
        newPetDto.setWeight(-1.0);
        newPetDto.setSpecies(Species.DOG);
        newPetDto.setBirthDate(LocalDate.now());
        var violations = validator.validate(newPetDto);
        assertEquals(3, violations.size());
    }

}
