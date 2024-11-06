package com.example.application.dto;

import com.example.application.utils.TranslationUtils;

public enum AddressType {

    BIRTH_ADDRESS("addressInfo.title.placeOfBirth"),
    CURRENT_ADDRESS("addressInfo.title.currentAddress"),
    REGISTRATION_ADDRESS("addressInfo.title.registerAddress");

    private final String translationKey;
    private String code;

    AddressType(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getCode() {
        if (code == null) {
            code = TranslationUtils.getTranslation(translationKey);
        }
        return code;
    }

    public static AddressType valueOff(String code) {
        for (AddressType type : AddressType.values()) {
            if (code.equals(type.getCode())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
