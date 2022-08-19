package com.example.translationapp;

public class TranslationText {
    private String translatedText;
    private String detectedSourceLanguage;

    public TranslationText() { }

    public TranslationText(String translatedText, String detectedSourceLanguage) {
        this.detectedSourceLanguage = detectedSourceLanguage;
        this.translatedText = translatedText;
    }

    public String getDetectedSourceLanguage() {
        return detectedSourceLanguage;
    }

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
