package com.dezoo.personeel.controllers;

import com.dezoo.personeel.models.PersonnelMember;
import com.dezoo.personeel.repositories.PersonnelRepository;
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
    private PersonnelRepository personnelRepository;

    /**
     * Gets all personnel from the db
     * @return A list of PersonnelMember objects, representing all of the personnel of the zoo
     */
    @GetMapping
    public List<PersonnelMember> getPersonnelMember() {
        return personnelRepository.findAll();
    }

    /**
     * Gets a single personnel member from the db
     * @param personnelID   The personnelID of the personnel member that has to be returned
     * @return              The personnel member which corresponds with the given personnelID
     */
    @GetMapping("/{personnelID}")
    public PersonnelMember getPersonnelMemberByID(@PathVariable String personnelID) {
        return personnelRepository.findPersonnelMemberByPersonnelId(personnelID);
    }

    /**
     * Adds a personnel member to the db
     * @param personnelMember   The personnel member that has to be added
     * @return                  The newly added personnel member
     */
    @PostMapping
    public ResponseEntity<PersonnelMember> postPersonnelMember(@RequestBody PersonnelMember personnelMember) {
        personnelRepository.save(personnelMember);

        return ResponseEntity.ok(personnelMember);
    }

    /**
     * Updates a personnel member
     * @param personnelMember   The personnel member that has to be updated
     * @return                  The newly updated personnel member
     */
    @PutMapping
    public ResponseEntity<PersonnelMember> putPersonnelMember(@RequestBody PersonnelMember personnelMember) {
        PersonnelMember personnelMember2 = personnelRepository.findPersonnelMemberByPersonnelId(personnelMember.getPersonnelId());

        personnelMember2.setAddress(personnelMember.getAddress());
        personnelMember2.setDateOfBirth(personnelMember.getDateOfBirth());
        personnelMember2.setFirstName(personnelMember.getFirstName());
        personnelMember2.setLastName(personnelMember.getLastName());
        personnelMember2.setPostalCode(personnelMember.getPostalCode());
        personnelMember2.setPrivatePhoneNumber(personnelMember.getPrivatePhoneNumber());
        personnelMember2.setPersonelCategory(personnelMember.getPersonelCategory());

        personnelRepository.save(personnelMember2);

        return ResponseEntity.ok(personnelMember2);
    }

    /**
     * Deletes a personnel member
     * @param personnelID   The personnelID of the personnel member that has to be deleted
     * @return              A response with either a "Not Found" code or an "OK" code
     */
    @DeleteMapping("/{personnelID}")
    public ResponseEntity<PersonnelMember> deletePersonnelMember(@PathVariable String personnelID) {
        PersonnelMember personnelMember = personnelRepository.findPersonnelMemberByPersonnelId(personnelID);
        if (personnelMember != null) {
            personnelRepository.delete(personnelMember);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @PostConstruct
    public void fillDBwithTestData() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            personnelRepository.save(new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration"));
            personnelRepository.save(new PersonnelMember("cn170999", "Christophe", "Neefs", format.parse("17/09/1999"), "Lier", "2500", "", "Rabbits"));
            personnelRepository.save(new PersonnelMember("rh031000", "Robbe", "Heremans", format.parse("03/10/2000"), "Westerlo", "2260", "", "Lions"));
        } catch (ParseException ignored) {
        }
    }
}
