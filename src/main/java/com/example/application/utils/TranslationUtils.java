package com.example.application.utils;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TranslationUtils {

    private static CustomI18NProvider i18nProvider;

    @Autowired
    public TranslationUtils(CustomI18NProvider provider) {
        i18nProvider = provider;
    }

    public static String getTranslation(String key, Object... params) {
        Locale locale = VaadinSession.getCurrent().getLocale();
        return i18nProvider.getTranslation(key, locale, params);
    }
}
