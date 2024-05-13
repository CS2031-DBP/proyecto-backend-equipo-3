package project.petpals.activity.dtos;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;


//import jakarta.validation.Valid;
//import jakarta.validation.constraints.*;
//import lombok.Data;



@Data
public class NewActivityDto {
    @NotNull
    private String address;
    @NotNull
    private String name;
    @NotNull
    private LocalDateTime endDate;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private ActivityType activityType;

}







