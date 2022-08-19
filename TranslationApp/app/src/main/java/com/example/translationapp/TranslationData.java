package com.example.translationapp;

import java.util.List;

public class TranslationData {
    private List<TranslationText> translations;

    public List<TranslationText> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationText> translations) {
        this.translations = translations;
    }
    
    public TranslationText[] getTranslationTextArray() {
        return translations.toArray(new TranslationText[translations.size()]);
    }
    
    public String[] getTranslatedStrings() {
        String[] translatedStrings = new String[translations.size()];

        int i = 0;
        for (TranslationText var : translations)
        {
            translatedStrings[i] = var.getTranslatedText();
            i++;
        }

        return translatedStrings;
    }
}
