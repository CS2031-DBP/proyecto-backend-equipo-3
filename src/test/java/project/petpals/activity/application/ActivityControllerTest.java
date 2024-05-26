package project.petpals.activity.application;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import project.petpals.Reader;
import project.petpals.activity.domain.Activity;
import project.petpals.activity.domain.ActivityStatus;
import project.petpals.activity.domain.ActivityType;
import project.petpals.activity.infrastructure.ActivityRepository;
import project.petpals.auth.dtos.RegisterRequest;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.NotFoundException;
import project.petpals.person.domain.Person;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = "perruanos@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@WithMockUser(roles = "COMPANY")
@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    Reader reader;

    @Autowired
    ObjectMapper objectMapper;

    private Long createActivity() {
        Activity activity = new Activity();
        activity.setActivityStatus(ActivityStatus.IN_PROGRESS);
        activity.setName("Tortugas Veloces");
        activity.setStartDate(LocalDate.of(2024,1,1));
        activity.setEndDate(LocalDate.of(2024,12,1));
        activity.setActivityType(ActivityType.PET_CONTEST);

        Company company = companyRepository.findByEmail("perruanos@example.com").orElseThrow(
                ()-> new NotFoundException("Testing company not found")
        );
        activity.setCompany(company);
        return activityRepository.save(activity).getId();
    }

    String token = "";

    @BeforeEach
    public void setup() throws Exception {
        activityRepository.deleteAll();
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
    public void testAuthorizedPostActivity() throws Exception {
        String activityJson = Reader.readJsonFile("activity/post.json");

        mockMvc.perform(post("/activities")
                        .contentType(APPLICATION_JSON)
                        .content(activityJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "PERSON")
    public void testUnauthorizedPostActivity() throws Exception {
        String activityJson = Reader.readJsonFile("activity/post.json");

        mockMvc.perform(post("/activities")
                        .contentType(APPLICATION_JSON)
                        .content(activityJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetActivityByType() throws Exception {
        createActivity();
        var response = mockMvc.perform(get("/activities/type/{type}", "PET_CONTEST")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();

        JSONObject responseJson = new JSONObject(Objects.requireNonNull(response.getResponse().getContentAsString()));
        assertEquals(1, responseJson.getInt("totalElements"));
        assertEquals("Tortugas Veloces", responseJson.getJSONArray("content").getJSONObject(0).getString("name"));
    }

    @Test
    public void testGetActivitiesByDate() throws Exception {
        createActivity();
        var response = mockMvc.perform(get("/activities/date/{date}", "2023-12-12")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();

        JSONObject responseJson = new JSONObject(Objects.requireNonNull(response.getResponse().getContentAsString()));
        assertEquals(1, responseJson.getInt("totalElements"));
        assertEquals("Tortugas Veloces", responseJson.getJSONArray("content").getJSONObject(0).getString("name"));
    }

    @Test
    public void testGetActivitiesByStatus() throws Exception {
        createActivity();
        var response = mockMvc.perform(get("/activities/status/{status}", "IN_PROGRESS")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();

        JSONObject responseJson = new JSONObject(Objects.requireNonNull(response.getResponse().getContentAsString()));
        assertEquals(1, responseJson.getInt("totalElements"));
        assertEquals("Tortugas Veloces", responseJson.getJSONArray("content").getJSONObject(0).getString("name"));
    }

    @Test
    public void testGetActivitiesByCompany() throws Exception {
        Long companyId = companyRepository.findByEmail("perruanos@example.com").orElseThrow(
                ()-> new NotFoundException("Testing company not found")
        ).getId();
        createActivity();
        var response = mockMvc.perform(get("/activities/company/{companyId}", companyId)
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();

        JSONObject responseJson = new JSONObject(Objects.requireNonNull(response.getResponse().getContentAsString()));
        assertEquals(1, responseJson.getInt("totalElements"));
        assertEquals("Tortugas Veloces", responseJson.getJSONArray("content").getJSONObject(0).getString("name"));
    }

    @Test
    @WithMockUser(roles = "PERSON")
    public void testUnauthorizedPersonDeleteActivity() throws Exception {
        Long activityId = createActivity();
        mockMvc.perform(delete("/activities/{id}", activityId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "gatillos@example.com", roles = "COMPANY")
    public void testUnauthorizedCompanyDeleteActivity() throws Exception {
        RegisterRequest unauthorizedCompany = new RegisterRequest();
        unauthorizedCompany.setIsCompany(true);
        unauthorizedCompany.setRuc("123456781");
        unauthorizedCompany.setPassword("123456789");
        unauthorizedCompany.setName("Gatillos");
        unauthorizedCompany.setEmail("gatillos@example.com");
        var res = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unauthorizedCompany)))
                .andExpect(status().isOk()).andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        String newToken = jsonObject.getString("token");

        Long activityId = createActivity();
        mockMvc.perform(delete("/activities/{id}", activityId)
                        .header("Authorization", "Bearer " + newToken))
                .andExpect(status().isForbidden());
    }

}
