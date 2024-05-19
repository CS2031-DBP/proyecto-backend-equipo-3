package project.petpals.auth.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    String name;
    String email;
    String password;
    Boolean isCompany;
    String ruc;
}
