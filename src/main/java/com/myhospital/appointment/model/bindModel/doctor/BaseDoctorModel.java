package com.myhospital.appointment.model.bindModel.doctor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BaseDoctorModel {
    @NotBlank(message = "{lang.doctor.name.mandatory}")
    @Pattern(regexp = "^[A-Za-z]*$",message = "{lang.doctor.name.only.chars}")
    private String name;

    @NotBlank(message = "{lang.doctor.surname.mandatory}")
    @Pattern(regexp = "^[A-Za-z]*$",message = "{lang.doctor.surname.only.chars}")
    private String surname;

    @NotBlank(message = "{lang.doctor.gender.mandatory}")
    private String gender;

    @NotBlank(message = "{lang.doctor.address.mandatory}")
    private String address;

    @Pattern(regexp ="[+][0-9\\s]{12}",message = "{lang.doctor.telephone.invalid.format}" )
    private String telephone;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "{lang.doctor.identity.invalid.format}")
    private String identityNumber;

    @NotBlank(message = "{lang.doctor.department.mandatory}")
    private String department;

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

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
