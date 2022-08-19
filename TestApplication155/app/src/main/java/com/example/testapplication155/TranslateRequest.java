package com.example.testapplication155;
/*
    This class matches the format that the Google Translate API expects request bodies to be in.
    GSON can use an object of this class, instantiated in the typical Java fashion as an object, to
    create a post request body to send to the Google Translate API
 */
public class TranslateRequest {
    // Body consists of:
    // q: array of strings to be translated
    // target: language to translate text to
    String[] q;
    String target;

    // Constructor
    public TranslateRequest(String[] q, String target) {
        this.q = q;
        this.target = target;
    }

    // Getters and setters
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
