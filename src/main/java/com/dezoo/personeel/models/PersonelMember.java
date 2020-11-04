package com.dezoo.personeel.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personel_members")
public class PersonelMember {
    //Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String personelId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    private String address;
    private String postalCode;
    private String privatePhoneNumber;

    //Constructors
    public PersonelMember() {
    }

    public PersonelMember(int id, String personelId, String firstName, String lastName, Date dateOfBirth, String address, String postalCode, String privatePhoneNumber) {
        Id = id;
        this.personelId = personelId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.postalCode = postalCode;
        this.privatePhoneNumber = privatePhoneNumber;
    }

    public PersonelMember(String personelId, String firstName, String lastName, Date dateOfBirth, String address, String postalCode, String privatePhoneNumber) {
        this.personelId = personelId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.postalCode = postalCode;
        this.privatePhoneNumber = privatePhoneNumber;
    }

    //getters and setters

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPersonelId() {
        return personelId;
    }

    public void setPersonelId(String personelId) {
        this.personelId = personelId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPrivatePhoneNumber() {
        return privatePhoneNumber;
    }

    public void setPrivatePhoneNumber(String privatePhoneNumber) {
        this.privatePhoneNumber = privatePhoneNumber;
    }
}
