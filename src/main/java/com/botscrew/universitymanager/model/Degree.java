package com.botscrew.universitymanager.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Degree {
    ASSISTANT("assistant"),
    ASSOCIATE_PROFESSOR("associate professor"),
    PROFESSOR("professor");

    private String value;

    Degree(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
