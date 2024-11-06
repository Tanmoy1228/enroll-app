package com.example.application.dto;

import com.example.application.utils.TranslationUtils;

public enum ContactType {

    MOBILE_PHONE("contactInfo.contactType.mobile"),
    HOME_PHONE("contactInfo.contactType.home"),
    WORK_PHONE("contactInfo.contactType.work"),
    EMAIL("contactInfo.contactType.email"),
    FAX("contactInfo.contactType.fax"),
    OFFICE_PHONE("contactInfo.contactType.office");

    private final String translationKey;
    private String code;

    ContactType(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getCode() {
        if (code == null) {
            code = TranslationUtils.getTranslation(translationKey);
        }
        return code;
    }

    public static ContactType valueOff(String code) {
        for (ContactType type : ContactType.values()) {
            if (code.equals(type.getCode())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
