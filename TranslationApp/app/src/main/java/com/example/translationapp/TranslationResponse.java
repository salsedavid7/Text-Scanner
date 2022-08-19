package com.example.translationapp;

public class TranslationResponse {
    private TranslationData data;

    public TranslationResponse(TranslationData data) {
        this.data = data;
    }

    public TranslationData getData() {
        return data;
    }

    public void setData(TranslationData data) {
        this.data = data;
    }
}
