package project.petpals.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.NotFoundException;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.user.infrastructure.BaseUserRepository;

@Service
public class UserService {
    @Autowired
    private BaseUserRepository<User> userRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public User findByEmail(String username, String role) {
        User user;
        if (role.equals("ROLE_PERSON"))
            user = personRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found!"));
        else
            user = companyRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found!"));

        return user;
    }

    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new NotFoundException("User not found!"));
            return (UserDetails) user;
        };
    }
}
