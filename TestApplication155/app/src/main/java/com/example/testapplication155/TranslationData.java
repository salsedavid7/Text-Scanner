package com.example.testapplication155;
/*
    This class matches the format of the data object returned in Google Translate's response JSON.
    This object contains a list of translatedText objects
 */
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

    // Getter that first converts the list of translatedText objects into an array of the
    // translatedText objects' text member variables
    public String[] getTranslatedStrings() {
        String[] translatedStrings = new String[translations.size()];

        int i = 0;
        // Iterate over list of TranslatedText objects in translations
        for (TranslationText var : translations)
        {
            // For each one, get the string of actual translated text it contains, and add to the
            // array allocated above
            translatedStrings[i] = var.getTranslatedText();
            i++;
        }

        return translatedStrings;
    }
}
