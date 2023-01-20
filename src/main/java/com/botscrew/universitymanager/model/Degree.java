package com.botscrew.universitymanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Degree {
    @JsonProperty("assistant") ASSISTANT,
    @JsonProperty("associate professor") ASSOCIATE_PROFESSOR,
    @JsonProperty("professor") PROFESSOR

}
