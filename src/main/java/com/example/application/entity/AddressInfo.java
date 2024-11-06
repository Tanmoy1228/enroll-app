package com.example.application.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address_info")
public class AddressInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable=false)
    private String email;

    @Column(name = "type", columnDefinition = "VARCHAR(20)", nullable=false)
    private String type;

    @Column(name = "country_id", nullable=false)
    private Long countryId;

    @Column(name = "province_id", nullable=false)
    private Long provinceId;

    @Column(name = "region_id", nullable=false)
    private Long regionId;

    @Column(name = "city_id", nullable=false)
    private Long cityId;

    @Column(name = "address_line", columnDefinition = "VARCHAR(100)", nullable=false)
    private String addressLine;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
}
