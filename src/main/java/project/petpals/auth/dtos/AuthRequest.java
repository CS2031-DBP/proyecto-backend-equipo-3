package project.petpals.auth.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    String email;
    String password;
}
