package com.example.grammatisch.astregex;

public record CallStep(String callee) implements RegexStep {
    public String toString() {
        return String.format("(?&%s)", callee);
    }
}
