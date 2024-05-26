package project.petpals.subscription.application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import project.petpals.Reader;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.NotFoundException;
import project.petpals.person.domain.Person;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.subscription.domain.PersonCompanyId;
import project.petpals.subscription.domain.Status;
import project.petpals.subscription.domain.Subscription;
import project.petpals.subscription.domain.SubscriptionService;
import project.petpals.subscription.infrastructure.SubscriptionRepository;
import project.petpals.user.domain.Role;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SubscriptionControllerTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    Reader reader;

    @Autowired
    private MockMvc mockMvc;

    String companyToken;
    String personToken;
    @Autowired
    private SubscriptionService subscriptionService;

    private void createSubscription() {
        subscriptionRepository.deleteAll();
        Subscription subscription = new Subscription();
        subscription.setStatus(Status.ACTIVE);
        subscription.setReceiveNotifs(true);
        subscription.setSubscriptionDate(LocalDateTime.now());
        Person person = personRepository.findByEmail("gadiel@example.com").orElseThrow(
                ()-> new NotFoundException("Testing person not found"));
        subscription.setPerson(person);
        Company company =  companyRepository.findByEmail("perruanos@example.com").orElseThrow(
                ()-> new NotFoundException("Testing company not found"));
        subscription.setCompany(company);
        subscription.setId(new PersonCompanyId(person.getId(), company.getId()));
        subscriptionRepository.save(subscription);
    }

    @BeforeEach
    public void setUp() throws Exception {
        subscriptionRepository.deleteAll();
        companyRepository.deleteAll();
        personRepository.deleteAll();


        String companyJson = Reader.readJsonFile("/company/register.json");
        var res1 = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(companyJson))
                .andExpect(status().isOk()) // mayhaps delete this? or change to created.
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res1.getResponse().getContentAsString()));
        companyToken = jsonObject.getString("token");
        System.out.println(companyRepository.findAll().get(0).toString());

        String personJson = Reader.readJsonFile("/person/register.json");
        var res2 = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isOk()) // mayhaps delete this? or change to created.
                .andReturn();
        JSONObject jsonObject2 = new JSONObject(Objects.requireNonNull(res2.getResponse().getContentAsString()));
        personToken = jsonObject2.getString("token");
    }

    @Test
    public void testAuthorizedPostSubscriptionAsPerson() throws Exception {
        String subscriptionJson = Reader.readJsonFile("/subscription/post.json");
        Company company = companyRepository.findAll().get(0);
        mockMvc.perform(post("/subscriptions/{companyId}", company.getId())
                        .contentType(APPLICATION_JSON)
                        .content(subscriptionJson)
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isCreated());
        // do some assertions to verify the correct creation of the subscription
    }

    @Test
    public void testUnallowedPostSubscriptionAsCompany() throws Exception {
        String subscriptionJson = Reader.readJsonFile("/subscription/post.json");
        Company company = companyRepository.findAll().get(0);
        mockMvc.perform(post("/subscriptions/{companyId}",company.getId())
                        .contentType(APPLICATION_JSON)
                        .content(subscriptionJson)
                        .header("Authorization", "Bearer " + companyToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedGetSubscriptionsByCompany() throws Exception {
        createSubscription();
        var res = mockMvc.perform(get("/subscriptions/company")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + companyToken))
                .andExpect(status().isOk()).andReturn();

        JSONObject response = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals(1, response.getInt("totalElements"));
        assertEquals("ACTIVE", response.getJSONArray("content").getJSONObject(0).getString("status"));
//        System.out.println(response);
    }

    @Test void testUnauthorizedGetSubscriptionsByCompany() throws Exception {
        createSubscription();
        mockMvc.perform(get("/subscriptions/company")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedGetSubscriptionsByPerson() throws Exception {
        createSubscription();
        var res = mockMvc.perform(get("/subscriptions/person")
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isOk()).andReturn();

        JSONArray response = new JSONArray(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals(1, response.length());
        assertEquals("ACTIVE", response.getJSONObject(0).getString("status"));
    }

    @Test
    public void testUnauthorizedGetSubscriptionsByPerson() throws Exception {
        createSubscription();
        mockMvc.perform(get("/subscriptions/person")
                        .header("Authorization", "Bearer " + companyToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedDeleteSubscription() throws Exception {
        createSubscription();
        Subscription subscription = subscriptionRepository.findAll().get(0);
        mockMvc.perform(delete("/subscriptions/" + subscription.getId().getCompanyId())
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isNoContent());

        assertEquals(1, subscriptionRepository.findAll().size());
        assertEquals(Status.CANCELLED, subscriptionRepository.findAll().get(0).getStatus());
    }

    @Test
    @WithMockUser(value = "jesus@example.com", roles = "PERSON")
    public void testNonexistentSubscriptionDelete() throws Exception {
        Person unauthorizedPerson = new Person();
        unauthorizedPerson.setName("Jesus");
        unauthorizedPerson.setPassword("password");
        unauthorizedPerson.setLastUpdated(LocalDateTime.now());
        unauthorizedPerson.setCreated(LocalDateTime.now());
        unauthorizedPerson.setEmail("jesus@example.com");
        unauthorizedPerson.setRole(Role.PERSON);
        personRepository.save(unauthorizedPerson);

        createSubscription();
        Subscription subscription = subscriptionRepository.findAll().get(0);
        mockMvc.perform(delete("/subscriptions/" + subscription.getId().getCompanyId())
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteSubscriptionToNonexistentCompany() throws Exception {
        createSubscription();
        mockMvc.perform(delete("/subscriptions/90000")
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void attemptToSubscribeTwice() throws Exception {
        subscriptionRepository.deleteAll();
        String subscriptionJson = Reader.readJsonFile("/subscription/post.json");
        Company company = companyRepository.findAll().get(0);
        mockMvc.perform(post("/subscriptions/{companyId}", company.getId())
                        .contentType(APPLICATION_JSON)
                        .content(subscriptionJson)
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/subscriptions/{companyId}", company.getId())
                        .contentType(APPLICATION_JSON)
                        .content(subscriptionJson)
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isConflict());
        assertEquals(1, subscriptionRepository.findAll().size());

    }

}


/*
- Test create authorized subscription as a PERSON
- Test create authorized subscription as a COMPANY
- Test authorized get subscription by company as OWNER COMPANY
- Test unauthorized get subscription by company as a PERSON
- Test authorized get subscription by person as a PERSON
- Test unauthorized get subscription by person as a COMPANY
- Test authorized delete subscription as a PERSON
- Test authorized delete subscription to nonexistent subscription as a PERSON (use invalid company id)
- Attempt to subscribe to the same company twice
 */
