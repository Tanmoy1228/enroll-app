package com.example.application.entity;

import com.example.application.dto.RelativeType;

import java.time.LocalDate;

public class RelativeInfo {

    private String name;

    private String surname;

    private String nativeName;

    private String nativeSurname;

    private String patronymic;

    private LocalDate birthDate;

    private String branch;

    private String workPosition;

    private String workPlace;

    private RelativeType relationship;

    private RelativeContact relativeContact;

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

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNativeSurname() {
        return nativeSurname;
    }

    public void setNativeSurname(String nativeSurname) {
        this.nativeSurname = nativeSurname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public RelativeContact getRelativeContact() {
        return relativeContact;
    }

    public void setRelativeContact(RelativeContact relativeContact) {
        this.relativeContact = relativeContact;
    }

    public RelativeType getRelationship() {
        return relationship;
    }

    public void setRelationship(RelativeType relationship) {
        this.relationship = relationship;
    }
}
