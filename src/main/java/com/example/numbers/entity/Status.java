package com.example.numbers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("active")
    ACTIVE,
    @JsonProperty("non_active")
    NOT_ACTIVE,
    @JsonProperty("deleted")
    DELETED
}
