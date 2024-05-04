package project.petpals.company.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyTest {

    @Test
    void testCompany() {
        Company company = new Company();
        company.setUsername("Losientoesperamedejamepensar");
        company.setEmail("losientoesperamedejame@gmail.com");
        company.setPassword("123456");
        company.setRuc("kdajhflaksdj");
        company.setCreated(LocalDateTime.of(2020,1,1,3,3));
        company.setLastUpdated(LocalDateTime.of(2020,1,1,3,3));

        assertEquals("Losientoesperamedejamepensar", company.getUsername());
        assertEquals("losientoesperamedejame@gmail.com", company.getEmail());
        assertEquals("123456", company.getPassword());
        assertEquals("kdajhflaksdj", company.getRuc());
        assertEquals(LocalDateTime.of(2020,1,1,3,3), company.getCreated());
        assertEquals(LocalDateTime.of(2020,1,1,3,3), company.getLastUpdated());
    }
}
