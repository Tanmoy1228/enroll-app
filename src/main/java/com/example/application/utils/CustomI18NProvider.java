package com.example.application.utils;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class CustomI18NProvider implements I18NProvider {

    public static final String BUNDLE_PREFIX = "messages";

    @Override
    public List<Locale> getProvidedLocales() {
        return Arrays.asList(new Locale("en"), new Locale("ru"), new Locale("ky"));
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);
        if (bundle.containsKey(key)) {
            return String.format(bundle.getString(key), params);
        }

        return key;
    }
}
