package com.example.application.entity;

import com.example.application.dto.DocumentType;
import com.example.application.dto.Gender;
import com.example.application.dto.MaritalStatus;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Entity
public class BasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable=false, unique = true)
    private String email;

    @Column(name = "first_name", columnDefinition = "VARCHAR(255)", nullable=false)
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(255)", nullable=false)
    private String lastName;

    @Column(name = "native_name", columnDefinition = "VARCHAR(255)", nullable=false)
    private String nativeName;

    @Column(name = "native_surname", columnDefinition = "VARCHAR(255)", nullable=false)
    private String nativeSurname;

    @Column(name = "patronymic", columnDefinition = "VARCHAR(255)")
    private String patronymic;

    @Column(name = "date_of_birth", nullable=false)
    private LocalDate dateOfBirth;

    @Column(name = "citizenship_id", columnDefinition = "VARCHAR(100)", nullable=false)
    private Long citizenshipId;

    @Column(name = "nationality_id", columnDefinition = "VARCHAR(100)", nullable=false)
    private Long nationalityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "VARCHAR(20)", nullable=false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", columnDefinition = "VARCHAR(20)", nullable=false)
    private MaritalStatus maritalStatus;

    @Column(name = "iin", columnDefinition = "VARCHAR(100)", nullable=false)
    private String iin;

    @Column(name = "military_registration")
    private String militaryRegistration;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", columnDefinition = "VARCHAR(100)", nullable=false)
    private DocumentType documentType;

    @Column(name = "document_no", columnDefinition = "VARCHAR(100)", nullable=false)
    private String documentNo;

    @Column(name = "issued_by", columnDefinition = "VARCHAR(255)", nullable=false)
    private String issuedBy;

    @Column(name = "issuance_date", nullable=false)
    private LocalDate issuanceDate;

    @Lob
    @Column(name = "document_image_1")
    private byte[] documentImage1;

    @Lob
    @Column(name = "document_image_2")
    private byte[] documentImage2;

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getCitizenshipId() {
        return citizenshipId;
    }

    public void setCitizenshipId(Long citizenship) {
        this.citizenshipId = citizenship;
    }

    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long nationality) {
        this.nationalityId = nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getMilitaryRegistration() {
        return militaryRegistration;
    }

    public void setMilitaryRegistration(String militaryRegistration) {
        this.militaryRegistration = militaryRegistration;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public LocalDate getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(LocalDate issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    @Transactional
    public byte[] getDocumentImage1() {
        return documentImage1;
    }

    public void setDocumentImage1(byte[] documentImage1) {
        this.documentImage1 = documentImage1;
    }

    @Transactional
    public byte[] getDocumentImage2() {
        return documentImage2;
    }

    public void setDocumentImage2(byte[] documentImages2) {
        this.documentImage2 = documentImages2;
    }
}
