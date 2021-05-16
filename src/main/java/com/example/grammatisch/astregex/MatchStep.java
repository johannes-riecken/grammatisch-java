package com.example.grammatisch.astregex;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MatchStep(@JsonProperty("matchString") String matchString) implements RegexStep {
    @Override
    public String toString() {
        return String.format("(?: %s )", matchString);
    }
}
