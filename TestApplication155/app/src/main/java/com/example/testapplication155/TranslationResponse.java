package com.example.testapplication155;
/*
    This class matches the format that Google Translate API responses will be in. OKhttp will return
    a string as a response to the API call, and GSON can be used to convert that string into an
    object of TranslationResponse type. The interface for that object can then be used to retrieve
    the response text.
 */
public class TranslationResponse {
    // Response consists of an outermost data object, with a translations attribute, which contains
    // an array of objects containing translatedText and detectedLanguage attributes
    private TranslationData data;

    // Constructor
    public TranslationResponse(TranslationData data) {
        this.data = data;
    }

    // Getters and setters
    // When GSON interprets the response as an object of this type, this function is called to
    // get the member data
    public TranslationData getData() {
        return data;
    }

    public void setData(TranslationData data) {
        this.data = data;
    }
}
