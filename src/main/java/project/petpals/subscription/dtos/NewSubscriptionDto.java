package project.petpals.subscription.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.petpals.person.dtos.PersonDto;

@Data
public class NewSubscriptionDto {
    @NotNull
    private Boolean receiveNotifs;
}
