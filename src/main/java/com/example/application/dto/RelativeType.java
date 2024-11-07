package com.example.application.dto;

import com.example.application.utils.TranslationUtils;

public enum RelativeType {

    FATHER("relativeInfo.relativeType.father"),
    MOTHER("relativeInfo.relativeType.mother"),
    BROTHER("relativeInfo.relativeType.brother"),
    SISTER("relativeInfo.relativeType.sister"),
    UNCLE("relativeInfo.relativeType.uncle"),
    AUNTY("relativeInfo.relativeType.aunty");

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

    public void refreshCode() {
        code = TranslationUtils.getTranslation(translationKey);
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
