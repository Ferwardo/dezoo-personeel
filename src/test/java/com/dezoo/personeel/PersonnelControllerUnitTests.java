package com.dezoo.personeel;

import com.dezoo.personeel.models.PersonnelMember;
import com.dezoo.personeel.repositories.PersonelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonnelControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonelRepository personelRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenPersonnelMember_whenGetPersonnelMember_thenReturnJsonPersonnelMembers() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        PersonnelMember personnelMember1 = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration");
        PersonnelMember personnelMember2 = new PersonnelMember("cn170999", "Christophe", "Neefs", format.parse("17/09/1999"), "Lier", "2500", "", "Rabbits");

        List<PersonnelMember> personnelMemberList = new ArrayList<>();
        personnelMemberList.add(personnelMember1);
        personnelMemberList.add(personnelMember2);

        given(personelRepository.findAll()).willReturn(personnelMemberList);

        mockMvc.perform(get("/personnel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
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
                .andExpect(jsonPath("[1].personelCategory", is("Rabbits")));
    }

    @Test
    public void givenPersonelMember_whenGetPersonnelMemberByID_thenReturnJsonPersonnelMember() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        PersonnelMember personnelMember1 = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration");

        given(personelRepository.findPersonnelMemberByPersonnelId("fs161100")).willReturn(personnelMember1);

        mockMvc.perform(get("/personnel/{personnelId}", "fs161100"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personnelId", is("fs161100")))
                .andExpect(jsonPath("$.firstName", is("Ferre")))
                .andExpect(jsonPath("$.lastName", is("Snyers")))
                .andExpect(jsonPath("$.dateOfBirth", is("2000-11-15T23:00:00.000+00:00")))
                .andExpect(jsonPath("$.address", is("Gestelstraat 21")))
                .andExpect(jsonPath("$.postalCode", is("2250")))
                .andExpect(jsonPath("$.privatePhoneNumber", is("+32441439")))
                .andExpect(jsonPath("$.personelCategory", is("Administration")));
    }

    @Test
    public void whenPostPersonnelMember_thenReturnJsonPersonnelMember() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        PersonnelMember personnelMember1 = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration");

        mockMvc.perform(post("/personnel")
                .content(mapper.writeValueAsString(personnelMember1))
                .contentType(MediaType.APPLICATION_JSON))
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
    public void givenPersonnelMember_whenPutPersonnelMember_thenReturnJsonPersonnelMember() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        PersonnelMember personnelMember1 = new PersonnelMember(1,"fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration");

        given(personelRepository.findPersonnelMemberByPersonnelId("fs161100")).willReturn(personnelMember1);

        PersonnelMember updatedPersonnelMember = new PersonnelMember(1, "fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 22", "2250", "+32441439", "Administration");

        mockMvc.perform(put("/personnel")
                .content(mapper.writeValueAsString(updatedPersonnelMember))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
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
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        PersonnelMember personnelMember1 = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration");

        given(personelRepository.findPersonnelMemberByPersonnelId("fs161100")).willReturn(personnelMember1);

        mockMvc.perform(delete("/personnel/{personnelID}", "fs161100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoReview_whenDeleteReview_thenStatusNotFound() throws Exception{
        given(personelRepository.findPersonnelMemberByPersonnelId("ff161100")).willReturn(null);

        mockMvc.perform(delete("/personnel/{personnelID}", "ff161100")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
