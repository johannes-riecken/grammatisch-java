package com.example.grammatisch.astregex;

public record MatchStep(String matchString) implements RegexStep {
    @Override
    public String toString() {
        return String.format("(?: %s )", matchString);
    }
}
