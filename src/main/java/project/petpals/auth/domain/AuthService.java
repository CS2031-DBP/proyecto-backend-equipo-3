package project.petpals.auth.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.petpals.auth.dtos.AuthRequest;
import project.petpals.auth.dtos.AuthResponse;
import project.petpals.auth.dtos.RegisterRequest;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.config.JwtService;
import project.petpals.exceptions.ConflictException;
import project.petpals.exceptions.NotFoundException;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.user.domain.Role;
import project.petpals.user.domain.User;
import project.petpals.user.infrastructure.BaseUserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    final BaseUserRepository<User> userRepository;
    final JwtService jwtService;
    final PasswordEncoder passwordEncoder;
    final ModelMapper modelMapper;
    final PersonRepository personRepository;
    final CompanyRepository companyRepository;

    @Autowired
    public AuthService(BaseUserRepository<User> userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, PersonRepository personRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
    }


    public AuthResponse register(RegisterRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) throw new ConflictException("Email is already registered");

        AuthResponse response = new AuthResponse();

        if (request.getIsCompany()) {
            Company newCompany = modelMapper.map(request, Company.class);
            newCompany.setPassword(passwordEncoder.encode(request.getPassword()));
            newCompany.setCreated(LocalDateTime.now());
            newCompany.setLastUpdated(LocalDateTime.now());
            newCompany.setRole(Role.COMPANY);
            companyRepository.save(newCompany);
            response.setToken(jwtService.generateToken(newCompany));
        }
        else {
            Person newPerson = modelMapper.map(request, Person.class);
            newPerson.setPassword(passwordEncoder.encode(request.getPassword()));
            newPerson.setCreated(LocalDateTime.now());
            newPerson.setLastUpdated(LocalDateTime.now());
            newPerson.setRole(Role.PERSON);
            personRepository.save(newPerson);
            response.setToken(jwtService.generateToken(newPerson));
        }

        return response;
    }


    public AuthResponse login(AuthRequest request) {
            Optional<User> user = userRepository.findByEmail(request.getEmail());

            if (user.isEmpty()) throw new NotFoundException("Email is not registered");

            if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword()))
                throw new IllegalArgumentException("Password is incorrect");

            AuthResponse response = new AuthResponse();

            response.setToken(jwtService.generateToken(user.get()));
            return response;

    }
}
