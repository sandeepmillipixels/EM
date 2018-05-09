package com.application.millipixels.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {
    Map<String,ArrayList<String>> error = new HashMap<>();

    public Map<String, ArrayList<String>> getError() {
        return error;
    }

    public void setError(Map<String, ArrayList<String>> error) {
        this.error = error;
    }
}
