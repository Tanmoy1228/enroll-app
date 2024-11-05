package com.example.application.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "education_info")
public class EducationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable=false, unique = true)
    private String email;

    @Column(name = "level_id", nullable=false)
    private Long levelId;

    @Column(name = "course_id", nullable=false)
    private Long courseId;

    @Column(name = "country_id", nullable=false)
    private Long countryId;

    @Column(name = "address_id", nullable=false)
    private Long addressId;

    @Column(name = "school_id", nullable=false)
    private Long schoolId;

    @Column(name = "language_id", nullable=false)
    private Long languageId;

    @Column(name = "attestat_type_id", nullable=false)
    private Long attestatTypeId;

    @Column(name = "attestat_no", columnDefinition = "VARCHAR(20)", nullable=false)
    private String attestatNo;

    @Column(name = "given_date", nullable=false)
    private LocalDate givenDate;

    @Lob
    @Column(name = "document_image_1")
    private byte[] documentImage1;

    @Lob
    @Column(name = "document_image_2")
    private byte[] documentImage2;

    @Column(name = "attend_ort")
    private boolean attendOrt;

    @Column(name = "registration_number", columnDefinition = "VARCHAR(20)")
    private String registrationNumber;

    @Column(name = "exam_score")
    private double examScore;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Lob
    @Column(name = "ort_certificate_image")
    private byte[] ortCertificateImage;

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

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }



    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Long getAttestatTypeId() {
        return attestatTypeId;
    }

    public void setAttestatTypeId(Long attestatTypeId) {
        this.attestatTypeId = attestatTypeId;
    }

    public String getAttestatNo() {
        return attestatNo;
    }

    public void setAttestatNo(String attestatNo) {
        this.attestatNo = attestatNo;
    }

    public LocalDate getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(LocalDate givenDate) {
        this.givenDate = givenDate;
    }

    public byte[] getDocumentImage1() {
        return documentImage1;
    }

    public void setDocumentImage1(byte[] documentImage1) {
        this.documentImage1 = documentImage1;
    }

    public byte[] getDocumentImage2() {
        return documentImage2;
    }

    public void setDocumentImage2(byte[] documentImage2) {
        this.documentImage2 = documentImage2;
    }

    public boolean isAttendOrt() {
        return attendOrt;
    }

    public void setAttendOrt(boolean attendOrt) {
        this.attendOrt = attendOrt;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public double getExamScore() {
        return examScore;
    }

    public void setExamScore(double examScore) {
        this.examScore = examScore;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public byte[] getOrtCertificateImage() {
        return ortCertificateImage;
    }

    public void setOrtCertificateImage(byte[] ortCertificateImage) {
        this.ortCertificateImage = ortCertificateImage;
    }
}
