package com.example.translationapp;

public class TranslateRequest {
    String[] q;
    String target;

    public TranslateRequest(String[] q, String target) {
        this.q = q;
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public String[] getQ() {
        return q;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setQ(String[] q) {
        this.q = q;
    }
}
