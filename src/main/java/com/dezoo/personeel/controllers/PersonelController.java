package com.dezoo.personeel.controllers;

import com.dezoo.personeel.models.PersonelMember;
import com.dezoo.personeel.repositories.PersonelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class PersonelController {

    @Autowired
    private PersonelRepository personelRepository;

    @GetMapping
    public List<PersonelMember> getPersonelMember() {
        return personelRepository.findAll();
    }

    @PostConstruct
    public void fillDBwithTestData() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            personelRepository.save(new PersonelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439"));
            personelRepository.save(new PersonelMember("cn170999", "Christophe", "Neefs", format.parse("17/09/1999"), "Lier", "2500", ""));
            personelRepository.save(new PersonelMember("rh031000", "Robbe", "Heremans", format.parse("03/10/2000"), "Westerloo", "2260", ""));
        } catch (ParseException ignored) {
        }
    }
}
