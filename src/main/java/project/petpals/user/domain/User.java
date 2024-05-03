package project.petpals.user.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @Column(name = "email", nullable = false)
    private String email;

}
