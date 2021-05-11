package com.example.grammatisch.astregex;

public record PositionSaveStep() implements RegexStep {
    public String toString() {
        return "(?{ [$^R, pos()] })";
    }
}
