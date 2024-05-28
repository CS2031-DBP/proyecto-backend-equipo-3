package project.petpals;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public JavaMailSender mailSender() {
        return Mockito.mock(JavaMailSender.class);
    }
}