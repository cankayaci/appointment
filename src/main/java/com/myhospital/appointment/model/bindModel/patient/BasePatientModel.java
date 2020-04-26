package com.myhospital.appointment.model.bindModel.patient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BasePatientModel {

    @NotBlank(message = "{lang.patient.name.mandatory}")
    @Pattern(regexp = "^[A-Za-z]*$",message = "{lang.patient.name.only.chars}")
    private String name;

    @NotBlank(message = "{lang.patient.surname.mandatory}")
    @Pattern(regexp = "^[A-Za-z]*$",message = "{lang.patient.surname.only.chars}")
    private String surname;

    private String gender;

    @NotBlank(message = "{lang.patient.address.mandatory}")
    private String address;

    @Pattern(regexp ="[+][0-9\\s]{12}",message = "{lang.patient.telephone.invalid.format}" )
    private String telephone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
