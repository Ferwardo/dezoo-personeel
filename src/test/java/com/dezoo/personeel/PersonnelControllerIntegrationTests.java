package com.dezoo.personeel;

import com.dezoo.personeel.models.PersonnelMember;
import com.dezoo.personeel.repositories.PersonnelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonnelControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonnelRepository personnelRepository;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    PersonnelMember personnelMember1 = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration"),
            personnelMember2 = new PersonnelMember("cn170999", "Christophe", "Neefs", format.parse("17/09/1999"), "Lier", "2500", "", "Rabbits"),
            personnelMemberToBeDeleted = new PersonnelMember("rh031000", "Robbe", "Heremans", format.parse("03/10/2000"), "Westerlo", "2260", "", "Lions");

    public PersonnelControllerIntegrationTests() throws ParseException {
    }

    @BeforeEach
    public void beforeAllTests() {
        personnelRepository.save(personnelMember1);
        personnelRepository.save(personnelMember2);
        personnelRepository.save(personnelMemberToBeDeleted);
    }

    @AfterEach
    public void afterAllTest() {
        personnelRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenPersonnelMember_whenGetPersonnelMember_thenReturnJsonPersonnelMembers() throws Exception {
        mockMvc.perform(get("/personnel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[0].personnelId", is("fs161100")))
                .andExpect(jsonPath("[0].firstName", is("Ferre")))
                .andExpect(jsonPath("[0].lastName", is("Snyers")))
                .andExpect(jsonPath("[0].dateOfBirth", is("2000-11-16T00:00:00.000+00:00")))
                .andExpect(jsonPath("[0].address", is("Gestelstraat 21")))
                .andExpect(jsonPath("[0].postalCode", is("2250")))
                .andExpect(jsonPath("[0].privatePhoneNumber", is("+32441439")))
                .andExpect(jsonPath("[0].personelCategory", is("Administration")))

                .andExpect(jsonPath("[1].personnelId", is("cn170999")))
                .andExpect(jsonPath("[1].firstName", is("Christophe")))
                .andExpect(jsonPath("[1].lastName", is("Neefs")))
                .andExpect(jsonPath("[1].dateOfBirth", is("1999-09-17T00:00:00.000+00:00")))
                .andExpect(jsonPath("[1].address", is("Lier")))
                .andExpect(jsonPath("[1].postalCode", is("2500")))
                .andExpect(jsonPath("[1].privatePhoneNumber", is("")))
                .andExpect(jsonPath("[1].personelCategory", is("Rabbits")))

                .andExpect(jsonPath("[2].personnelId", is("rh031000")))
                .andExpect(jsonPath("[2].firstName", is("Robbe")))
                .andExpect(jsonPath("[2].lastName", is("Heremans")))
                .andExpect(jsonPath("[2].dateOfBirth", is("2000-10-03T00:00:00.000+00:00")))
                .andExpect(jsonPath("[2].address", is("Westerlo")))
                .andExpect(jsonPath("[2].postalCode", is("2260")))
                .andExpect(jsonPath("[2].privatePhoneNumber", is("")))
                .andExpect(jsonPath("[2].personelCategory", is("Lions")));
    }

    @Test
    public void givenPersonnelMember_whenGetPersonnelMemberByID_thenReturnJsonPersonnelMember() throws Exception {
        mockMvc.perform(get("/personnel/{personnelId}", "fs161100"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personnelId", is("fs161100")))
                .andExpect(jsonPath("$.firstName", is("Ferre")))
                .andExpect(jsonPath("$.lastName", is("Snyers")))
                .andExpect(jsonPath("$.dateOfBirth", is("2000-11-16T00:00:00.000+00:00")))
                .andExpect(jsonPath("$.address", is("Gestelstraat 21")))
                .andExpect(jsonPath("$.postalCode", is("2250")))
                .andExpect(jsonPath("$.privatePhoneNumber", is("+32441439")))
                .andExpect(jsonPath("$.personelCategory", is("Administration")));
    }

    @Test
    public void whenPostPersonnelMember_thenReturnJsonPersonnelMember() throws Exception {
        PersonnelMember personnelMember3 = new PersonnelMember("dj111100", "Dirk", "Jansens", format.parse("11/11/2000"), "Gestelstraat 22", "2250", "", "Administration");

        mockMvc.perform(post("/personnel")
                .content(mapper.writeValueAsString(personnelMember3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personnelId", is("dj111100")))
                .andExpect(jsonPath("$.firstName", is("Dirk")))
                .andExpect(jsonPath("$.lastName", is("Jansens")))
                .andExpect(jsonPath("$.dateOfBirth", is("2000-11-11T00:00:00.000+00:00")))
                .andExpect(jsonPath("$.address", is("Gestelstraat 22")))
                .andExpect(jsonPath("$.postalCode", is("2250")))
                .andExpect(jsonPath("$.privatePhoneNumber", is("")))
                .andExpect(jsonPath("$.personelCategory", is("Administration")));
    }

    @Test
    public void givenPersonnelMember_whenPutPersonnelMember_thenReturnJsonPersonnelMember() throws Exception {
        PersonnelMember updatedPersonnelMember = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 22", "2250", "+32441439", "Administration");

        mockMvc.perform(put("/personnel")
                .content(mapper.writeValueAsString(updatedPersonnelMember))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personnelId", is("fs161100")))
                .andExpect(jsonPath("$.firstName", is("Ferre")))
                .andExpect(jsonPath("$.lastName", is("Snyers")))
                .andExpect(jsonPath("$.dateOfBirth", is("2000-11-16T00:00:00.000+00:00")))
                .andExpect(jsonPath("$.address", is("Gestelstraat 22")))
                .andExpect(jsonPath("$.postalCode", is("2250")))
                .andExpect(jsonPath("$.privatePhoneNumber", is("+32441439")))
                .andExpect(jsonPath("$.personelCategory", is("Administration")));
    }

    @Test
    public void givenReview_WhenDeleteReview_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/personnel/{personnelID}", "fs161100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoReview_whenDeleteReview_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/personnel/{personnelID}", "ff161100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
