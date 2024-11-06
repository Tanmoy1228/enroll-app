package com.example.application.dto;

import com.example.application.utils.TranslationUtils;

public enum RelativeType {

    FATHER("relativeInfo.contactType.mobile"),
    MOTHER("contactInfo.contactType.home"),
    BROTHER("contactInfo.contactType.work"),
    SISTER("contactInfo.contactType.email"),
    UNCLE("contactInfo.contactType.fax"),
    AUNTY("contactInfo.contactType.office");

    private final String translationKey;
    private String code;

    RelativeType(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getCode() {
        if (code == null) {
            code = TranslationUtils.getTranslation(translationKey);
        }
        return code;
    }

    public static RelativeType valueOff(String code) {
        for (RelativeType type : RelativeType.values()) {
            if (code.equals(type.getCode())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
