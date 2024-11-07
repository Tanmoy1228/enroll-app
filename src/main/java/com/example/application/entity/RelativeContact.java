package com.example.application.entity;

import com.example.application.dto.ContactType;
import jakarta.persistence.*;

@Entity
@Table(name = "relative_contact")
public class RelativeContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "VARCHAR(20)", nullable = false)
    private ContactType type;

    @Column(name = "contact", columnDefinition = "VARCHAR(100)", nullable = false)
    private String contact;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "relative_info_id", referencedColumnName = "id", nullable = false)
    private RelativeInfo relativeInfo;

    public RelativeContact() {}

    public RelativeContact(ContactType type, String contact) {
        this.type = type;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public RelativeInfo getRelativeInfo() {
        return relativeInfo;
    }

    public void setRelativeInfo(RelativeInfo relativeInfo) {
        this.relativeInfo = relativeInfo;
    }
}
