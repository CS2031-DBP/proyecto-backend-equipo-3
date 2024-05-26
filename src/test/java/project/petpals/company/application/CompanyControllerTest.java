package project.petpals.company.application;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import project.petpals.Reader;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = "perruanos@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@WithMockUser(roles = "COMPANY")
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    Reader reader;

    String token = "";

    @BeforeEach
    public void setup() throws Exception {
        companyRepository.deleteAll();

        String companyJson = Reader.readJsonFile("company/register.json");

        var res = mockMvc.perform(post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isOk()) // mayhaps delete this? or change to created.
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
    }

    @Test
    public void testAuthorizedAccessGetAllCompanies() throws Exception {
        mockMvc.perform(get("/company")
                .header("Authorization", "Bearer " + token)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorizedAccessOneCompany() throws Exception {
        Company found = companyRepository.findByEmail("perruanos@example.com").get();
        mockMvc.perform(get("/company/{id} ", found.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSelf() throws Exception {
        var response = mockMvc.perform(get("/company/me")
                .header("Authorization", "Bearer " + token)).andReturn();

        JSONObject responseJson = new JSONObject(Objects.requireNonNull(response.getResponse().getContentAsString()));
        assertEquals("Perruanos", responseJson.getString("name"));
    }

    @Test
    public void testUpdateSelf() throws Exception {
        mockMvc.perform(patch("/company")
                        .contentType(APPLICATION_JSON)
                        .content(Reader.readJsonFile("/company/patch.json"))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        var response = mockMvc.perform(get("/company/me")
                .header("Authorization", "Bearer " + token)).andReturn();
        JSONObject responseJson = new JSONObject(Objects.requireNonNull(response.getResponse().getContentAsString()));
        assertEquals("Perruguay", responseJson.getString("name"));

    }

}
