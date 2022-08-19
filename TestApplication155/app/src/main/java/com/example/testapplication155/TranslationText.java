package com.example.testapplication155;
/*
    This class matches the format of the 'translations' object returned in Google Translate's
    response JSON. This object contains two strings for each string of foreign text that was sent to
    the Translate API - one containing the translated text (what we're interested in) and one
    containing the language detected by Google
 */
public class TranslationText {
    private String translatedText;
    private String detectedSourceLanguage;

    public TranslationText() { }

    // Constructor
    public TranslationText(String translatedText, String detectedSourceLanguage) {
        this.detectedSourceLanguage = detectedSourceLanguage;
        this.translatedText = translatedText;
    }

    public String getDetectedSourceLanguage() {
        return detectedSourceLanguage;
    }

    // Used to get the text that we want to display
    public String getTranslatedText() {
        return translatedText;
    }

    public void setDetectedSourceLanguage(String detectedSourceLanguage) {
        this.detectedSourceLanguage = detectedSourceLanguage;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
