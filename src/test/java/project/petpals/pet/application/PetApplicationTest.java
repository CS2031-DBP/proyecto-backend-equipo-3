package project.petpals.pet.application;

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
import project.petpals.adoption.domain.Adoption;
import project.petpals.adoption.domain.PetPersonId;
import project.petpals.adoption.infrastructure.AdoptionRepository;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.person.domain.Person;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WithMockUser(roles = "PERSON", setupBefore = TestExecutionEvent.TEST_EXECUTION)
//@WithUserDetails(value = "gadiel@example.com")
public class PetApplicationTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    Reader reader;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdoptionRepository adoptionRepository;

    String companyToken;
    String personToken;

    private Long createPet() {
        Pet pet = new Pet();
        pet.setPetStatus(PetStatus.IN_ADOPTION);
        pet.setDescription("Pelusa is a very friendly dog, she loves to play.");
        pet.setWeight(20.5);
        pet.setName("Pelusa");
        pet.setSpecies(Species.DOG);
        pet.setBreed("Golden Retriever");
        pet.setSex("female");
        pet.setCompany(companyRepository.findAll().get(0));
        pet.setBirthDate(LocalDate.of(2022, 1, 1));

        return petRepository.save(pet).getId();
    }


    @BeforeEach()
    void setup() throws Exception {
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
    }

    @Test
    public void testAuthorizedPostPetAsCompany() throws Exception {
        String petJson = Reader.readJsonFile("/pet/post.json");
        mockMvc.perform(post("/pets")
                        .contentType(APPLICATION_JSON)
                        .content(petJson)
                        .header("Authorization", "Bearer " + companyToken))
                .andExpect(status().isCreated());

        assertEquals(1, petRepository.count());

        Pet pet = petRepository.findAll().get(0);
        assertEquals("Pelusa", pet.getName());
        assertEquals("Golden Retriever", pet.getBreed());
        assertEquals("female", pet.getSex());
        assertEquals(Species.DOG, pet.getSpecies());
        assertEquals(20.5, pet.getWeight());
        assertEquals("Pelusa is a very friendly dog, she loves to play.", pet.getDescription());
        assertEquals(PetStatus.IN_ADOPTION, pet.getPetStatus());

    }

    @Test
    @WithMockUser(roles = "PERSON")
    public void testUnauthorizedPostPetAsPerson() throws Exception {
        String petJson = Reader.readJsonFile("/pet/post.json");
        mockMvc.perform(post("/pets")
                        .contentType(APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles={"PERSON", "COMPANY"})
    public void testAuthorizedGetPetByBreed() throws Exception {
        createPet();
        var res = mockMvc.perform(get("/pets/breed/{breed}", "Golden Retriever")
                .param("page", "0").param("size","5")).andExpect(status().isOk()).andDo(print()).andReturn();

        JSONObject response = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals(1, petRepository.count());
        assertEquals(1, response.getInt("totalElements"));
        JSONObject pet = response.getJSONArray("content").getJSONObject(0);
        assertEquals("Pelusa", pet.getString("name"));
        assertEquals("female", pet.getString("sex"));

    }

    @Test
    @WithMockUser(roles={"PERSON", "COMPANY"})
    public void testAuthorizedGetPetByCompany() throws Exception {
        createPet();
        var res = mockMvc.perform(get("/pets/company/{companyId}", companyRepository.findAll().get(0).getId())
                .param("page", "0").param("size","5")).andExpect(status().isOk()).andReturn();

        JSONObject response = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals(1, response.getInt("totalElements"));
        JSONObject pet = response.getJSONArray("content").getJSONObject(0);
        assertEquals("Pelusa", pet.getString("name"));
        assertEquals("Golden Retriever", pet.getString("breed"));
    }

    @Test
    @WithMockUser(roles={"PERSON", "COMPANY"})
    public void testAuthorizedGetAllInAdoption() throws Exception {
        createPet();
        var res = mockMvc.perform(get("/pets/inAdoption")
        .param("page", "0").param("size","5")).andExpect(status().isOk()).andReturn();

        JSONObject response = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals(1, response.getInt("totalElements"));
        JSONObject pet = response.getJSONArray("content").getJSONObject(0);
        assertEquals("Pelusa", pet.getString("name"));
        assertEquals("Golden Retriever", pet.getString("breed"));
    }

    @Test
    @WithMockUser(roles ={ "PERSON", "COMPANY"})
    public void testAuthorizedGetAllBySpecies() throws Exception {
        createPet();
        var res = mockMvc.perform(get("/pets/species/{species}", "DOG")
        .param("page", "0").param("size","5")).andExpect(status().isOk()).andReturn();

        JSONObject response = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals(1, response.getInt("totalElements"));
        JSONObject pet = response.getJSONArray("content").getJSONObject(0);
        assertEquals("Pelusa", pet.getString("name"));
        assertEquals("Golden Retriever", pet.getString("breed"));

        var res2 = mockMvc.perform(get("/pets/species/{species}", "CAT")
                .param("page", "0").param("size","5")).andExpect(status().isOk()).andReturn();
        JSONObject response2 = new JSONObject(Objects.requireNonNull(res2.getResponse().getContentAsString()));
        assertEquals(0, response2.getInt("totalElements"));

    }

    @Test
    public void testUpdatePetAsPerson() throws Exception {

        Long petId = createPet();

        String personJson = Reader.readJsonFile("/person/register.json");
        var res2 = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject2 = new JSONObject(Objects.requireNonNull(res2.getResponse().getContentAsString()));
        personToken = jsonObject2.getString("token");

        Adoption adoption = new Adoption();
        Company company = companyRepository.findAll().get(0);
        Person person = personRepository.findAll().get(0);
        Pet pet = petRepository.findAll().get(0);
        pet.setPetStatus(PetStatus.ADOPTED);
        adoption.setPerson(person);
        adoption.setCompany(company);
        adoption.setPet(pet);

        adoption.setId(new PetPersonId(pet.getId(), person.getId()));
        adoption.setDescription("Rescued her from the streets.");
        adoption.setAdoptionDate(LocalDate.now());
        adoptionRepository.save(adoption);

        String petJson = Reader.readJsonFile("/pet/patch.json");
        mockMvc.perform(patch("/pets/{id}", petId)
                        .contentType(APPLICATION_JSON)
                        .content(petJson)
                        .header("Authorization", "Bearer " + personToken))
                .andExpect(status().isOk());

        System.out.println(petRepository.findAll().get(0).getDescription());

    }

    @Test
    public void testAuthorizedGetPetByIdAsCompany() throws Exception {
        Long petId = createPet();
        var res = mockMvc.perform(get("/pets/{id}", petId.toString())
                .header("Authorization", "Bearer " + companyToken)).andExpect(status().isOk()).andReturn();
        JSONObject response = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals("Pelusa", response.getString("name"));
        assertEquals("Golden Retriever", response.getString("breed"));

    }

    @Test
    public void testAuthorizedGetPetByIdAsPerson() throws Exception {
        Long petId = createPet();

        String personJson = Reader.readJsonFile("/person/register.json");
        var res2 = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject2 = new JSONObject(Objects.requireNonNull(res2.getResponse().getContentAsString()));
        personToken = jsonObject2.getString("token");

        Adoption adoption = new Adoption();
        Company company = companyRepository.findAll().get(0);
        Person person = personRepository.findAll().get(0);
        Pet pet = petRepository.findAll().get(0);
        pet.setPetStatus(PetStatus.ADOPTED);
        adoption.setPerson(person);
        adoption.setCompany(company);
        adoption.setPet(pet);

        adoption.setId(new PetPersonId(pet.getId(), person.getId()));
        adoption.setDescription("Rescued her from the streets.");
        adoption.setAdoptionDate(LocalDate.now());
        adoptionRepository.save(adoption);

        var res = mockMvc.perform(get("/pets/{id}", petId)
                .header("Authorization","Bearer " + personToken)).andExpect(status().isOk()).andReturn();
        JSONObject response = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        assertEquals("Pelusa", response.getString("name"));
    }

}

/*
- Test authorized post of pet as COMPANY
- Test unauthorized post of pet as PERSON
- Test all authorized GETs as COMPANY and PERSON
- Test update pet as PERSON

 */
