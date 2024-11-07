package com.example.application.entity;

import com.example.application.dto.RelativeType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "relative_info")
public class RelativeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable=false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "VARCHAR(100)", nullable=false)
    private RelativeType Relationship;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable=false)
    private String name;

    @Column(name = "surname", columnDefinition = "VARCHAR(100)", nullable=false)
    private String surname;

    @Column(name = "native_name", columnDefinition = "VARCHAR(255)", nullable=false)
    private String nativeName;

    @Column(name = "native_surname", columnDefinition = "VARCHAR(255)", nullable=false)
    private String nativeSurname;

    @Column(name = "patronymic", columnDefinition = "VARCHAR(255)", nullable=false)
    private String patronymic;

    @Column(name = "date_of_birth", nullable=false)
    private LocalDate birthDate;

    @Column(name = "branch", columnDefinition = "VARCHAR(255)")
    private String branch;

    @Column(name = "work_position", columnDefinition = "VARCHAR(255)")
    private String workPosition;

    @Column(name = "work_place", columnDefinition = "VARCHAR(255)")
    private String workPlace;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "relativeInfo")
    private List<RelativeContact> relativeContacts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RelativeType getRelationship() {
        return Relationship;
    }

    public void setRelationship(RelativeType type) {
        this.Relationship = type;
    }

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

    public List<RelativeContact> getRelativeContacts() {
        return relativeContacts;
    }

    public void setRelativeContacts(List<RelativeContact> relativeContacts) {
        this.relativeContacts = relativeContacts;
    }
}
