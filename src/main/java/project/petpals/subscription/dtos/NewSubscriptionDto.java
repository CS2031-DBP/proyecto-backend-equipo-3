package project.petpals.subscription.dtos;

import jakarta.validation.constraints.NotNull;
import project.petpals.company.dtos.CompanyDto;
import project.petpals.person.dtos.PersonDto;

public class NewSubscriptionDto {
    @NotNull
    private CompanyDto company;
    @NotNull
    private PersonDto person;
    @NotNull
    private Boolean receiveNotifs;
}
