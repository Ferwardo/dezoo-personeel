package com.dezoo.personeel.controllers;

import com.dezoo.personeel.models.PersonnelMember;
import com.dezoo.personeel.repositories.PersonelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("personnel")
public class PersonnelController {

    @Autowired
    private PersonelRepository personelRepository;

    @GetMapping
    public List<PersonnelMember> getPersonnelMember() {
        return personelRepository.findAll();
    }

    @GetMapping("/{personnelID}")
    public PersonnelMember getPersonnelMemberByID(@PathVariable String personnelID) {
        return personelRepository.findPersonnelMemberByPersonnelId(personnelID);
    }

    @PostMapping
    public ResponseEntity<PersonnelMember> postPersonnelMember(@RequestBody PersonnelMember personnelMember) {
        personelRepository.save(personnelMember);

        return ResponseEntity.ok(personnelMember);
    }

    @PutMapping
    public ResponseEntity<PersonnelMember> putPersonnelMember(@RequestBody PersonnelMember personnelMember) {
        PersonnelMember personnelMember2 = personelRepository.findPersonnelMemberByPersonnelId(personnelMember.getPersonnelId());

        personnelMember2.setAddress(personnelMember.getAddress());
        personnelMember2.setDateOfBirth(personnelMember.getDateOfBirth());
        personnelMember2.setFirstName(personnelMember.getFirstName());
        personnelMember2.setLastName(personnelMember.getLastName());
        personnelMember2.setPostalCode(personnelMember.getPostalCode());
        personnelMember2.setPrivatePhoneNumber(personnelMember.getPrivatePhoneNumber());
        personnelMember2.setPersonelCategory(personnelMember.getPersonelCategory());

        personelRepository.save(personnelMember2);

        return ResponseEntity.ok(personnelMember2);
    }

    @DeleteMapping("/{personnelID}")
    public ResponseEntity<PersonnelMember> deletePersonnelMember(@PathVariable String personnelID){
        PersonnelMember personnelMember = personelRepository.findPersonnelMemberByPersonnelId(personnelID);
        personelRepository.delete(personnelMember);

        return ResponseEntity.ok(personnelMember);
    }

//    @PostConstruct
//    public void fillDBwithTestData() {
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            personelRepository.save(new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration"));
//            personelRepository.save(new PersonnelMember("cn170999", "Christophe", "Neefs", format.parse("17/09/1999"), "Lier", "2500", "", "Rabbits"));
//            personelRepository.save(new PersonnelMember("rh031000", "Robbe", "Heremans", format.parse("03/10/2000"), "Westerloo", "2260", "", "Lions"));
//        } catch (ParseException ignored) {
//        }
//    }
}
