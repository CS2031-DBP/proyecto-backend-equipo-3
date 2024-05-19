package project.petpals;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PetPalsApplication {

    public static void main(String[] args) {

        SpringApplication.run(PetPalsApplication.class, args);
        System.out.println("Hello World!");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
