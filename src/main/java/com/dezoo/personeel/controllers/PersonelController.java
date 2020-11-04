package com.dezoo.personeel.controllers;

import com.dezoo.personeel.models.PersonelMember;
import com.dezoo.personeel.repositories.PersonelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("personel")
public class PersonelController {

    @Autowired
    private PersonelRepository personelRepository;

    @GetMapping
    public List<PersonelMember> getPersonelMember() {
        return personelRepository.findAll();
    }

    @GetMapping("/{personelID}")
    public PersonelMember getPersonelMemberID(@PathVariable String personelID) {
        return personelRepository.findPersonelMemberByPersonelId(personelID);
    }

    @PostMapping
    public void postPersonelMember(@RequestBody PersonelMember personelMember) {
        personelRepository.save(personelMember);
    }

    @PutMapping("/{personelID}")
    public ResponseEntity<PersonelMember> putPersonelMember(@PathVariable String personelID, @RequestBody PersonelMember personelMember) {
        PersonelMember personelMember2 = personelRepository.findPersonelMemberByPersonelId(personelID);

        personelMember2.setAddress(personelMember.getAddress());
        personelMember2.setDateOfBirth(personelMember.getDateOfBirth());
        personelMember2.setFirstName(personelMember.getFirstName());
        personelMember2.setLastName(personelMember.getLastName());
        personelMember2.setPostalCode(personelMember.getPostalCode());
        personelMember2.setPrivatePhoneNumber(personelMember.getPrivatePhoneNumber());
        personelMember2.setPersonelCategory(personelMember.getPersonelCategory());

        personelMember2 = personelRepository.save(personelMember2);

        return ResponseEntity.ok(personelMember2);
    }

    @DeleteMapping("/{personelID}")
    public ResponseEntity<PersonelMember> deletePersonelMember(@PathVariable String personelID){
        PersonelMember personelMember = personelRepository.findPersonelMemberByPersonelId(personelID);
        personelRepository.delete(personelMember);

        return ResponseEntity.ok(personelMember);
    }

    @PostConstruct
    public void fillDBwithTestData() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            personelRepository.save(new PersonelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration"));
            personelRepository.save(new PersonelMember("cn170999", "Christophe", "Neefs", format.parse("17/09/1999"), "Lier", "2500", "", "Rabbits"));
            personelRepository.save(new PersonelMember("rh031000", "Robbe", "Heremans", format.parse("03/10/2000"), "Westerloo", "2260", "", "Lions"));
        } catch (ParseException ignored) {
        }
    }
}
