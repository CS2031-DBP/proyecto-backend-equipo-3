package project.petpals.adoption.application;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import project.petpals.Reader;
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.infrastructure.AdoptionRepository;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.person.infrastructure.PersonRepository;
import project.petpals.pet.domain.Pet;
import project.petpals.pet.domain.PetStatus;
import project.petpals.pet.domain.Species;
import project.petpals.pet.infrastructure.PetRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdoptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private Reader reader;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    String companyToken;
    String personToken;
    Pet pet;
    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    public void setup() throws Exception {
        adoptionRepository.deleteAll();
        petRepository.deleteAll();
        companyRepository.deleteAll();
        personRepository.deleteAll();


        String companyJson = Reader.readJsonFile("/company/register.json");
        var res1 = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(companyJson))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res1.getResponse().getContentAsString()));
        companyToken = jsonObject.getString("token");
        System.out.println(companyRepository.findAll().get(0).toString());

        String personJson = Reader.readJsonFile("/person/register.json");
        var res2 = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject2 = new JSONObject(Objects.requireNonNull(res2.getResponse().getContentAsString()));
        personToken = jsonObject2.getString("token");

        pet = new Pet();
        pet.setPetStatus(PetStatus.IN_ADOPTION);
        pet.setWeight(20.5);
        pet.setDescription("Pelusa is a very friendly dog, she loves to play.");
        pet.setCompany(companyRepository.findAll().get(0));
        pet.setSex("female");
        pet.setBreed("Golden Retriever");
        pet.setName("Pelusa");
        pet.setBirthDate(LocalDate.of(2022, 1,1));
        pet.setSpecies(Species.DOG);
    }


    @AfterEach
    void end() {
        adoptionRepository.deleteAll();
        petRepository.deleteAll();
        companyRepository.deleteAll();
    }
    @Test
    public void testSaveAdoption() throws Exception {
        String adoptionJson = Reader.readJsonFile("/adoption/post.json");
        Long petId = petRepository.save(pet).getId();
        System.out.println(adoptionJson);
        mockMvc.perform(post("/adoptions/{petId}", petId)
                        .header("Authorization", "Bearer " + personToken)
                        .contentType(APPLICATION_JSON)
                        .content(adoptionJson))
                .andExpect(status().isCreated());
        Adoption savedAdoption = adoptionRepository.findAll().get(0);
        assertEquals(pet.getId(), savedAdoption.getPet().getId());
        assertEquals(personRepository.findAll().get(0).getId(), savedAdoption.getPerson().getId());
        assertEquals(companyRepository.findAll().get(0).getId(), savedAdoption.getCompany().getId());
        assertEquals(LocalDate.now(), savedAdoption.getAdoptionDate());
        assertEquals("I want to adopt this pet", savedAdoption.getDescription());
    }

    @Test
    public void testSaveAdoptionPetAlreadyAdopted() throws Exception {
        String adoptionJson = Reader.readJsonFile("/adoption/post.json");
        pet.setPetStatus(PetStatus.ADOPTED);
        Long petId = petRepository.save(pet).getId();
        petRepository.save(pet);
        mockMvc.perform(post("/adoptions/{petId}", petId)
                        .header("Authorization", "Bearer " + personToken)
                        .contentType(APPLICATION_JSON)
                        .content(adoptionJson))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/adoptions/{petId}", petId)
                        .header("Authorization", "Bearer " + personToken)
                        .contentType(APPLICATION_JSON)
                        .content(adoptionJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void getAuthorizedAdoption() throws Exception {
        String adoptionJson = Reader.readJsonFile("/adoption/post.json");
        Long petId = petRepository.save(pet).getId();
        mockMvc.perform(post("/adoptions/{petId}", petId)
                        .header("Authorization", "Bearer " + personToken)
                        .contentType(APPLICATION_JSON)
                        .content(adoptionJson))
                .andExpect(status().isCreated());
//        Adoption savedAdoption = adoptionRepository.findAll().get(0);
        var res = mockMvc.perform(get("/adoptions/mine")
                        .header("Authorization", "Bearer " + personToken)
                        .param("page","0").param("size","10"))
                .andExpect(status().isOk()).andReturn();

        JSONObject response = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals(1, response.getInt("totalElements"));
        assertEquals("I want to adopt this pet", response.getJSONArray("content").getJSONObject(0).getString("description"));
    }

    @Test
    public void testUnauthorizedCompanyGetPersonAdoptions() throws Exception {
        mockMvc.perform(get("/adoptions/mine")
                        .header("Authorization", "Bearer " + companyToken)
                .param("page","0").param("size","10"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "other@example.com", roles = "PERSON")
    public void testAccessToNonexistentAdoption() throws Exception {
        Long petId = petRepository.save(pet).getId();
//        String adoptionJson = Reader.readJsonFile("/adoption/post.json");
//        petRepository.save(pet);
//        mockMvc.perform(post("/adoptions")
//                        .header("Authorization", "Bearer " + personToken)
//                        .contentType(APPLICATION_JSON)
//                        .content(adoptionJson))
//                .andExpect(status().isCreated());
//        Adoption savedAdoption = adoptionRepository.findAll().get(0);
        mockMvc.perform(get("/adoptions/{petId}/{personId}",petId, personRepository.findAll().get(0).getId())
                        .param("page","0").param("size","10"))
                .andExpect(status().isNotFound());
    }
}
