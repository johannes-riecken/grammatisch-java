package com.example.grammatisch.astregex;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CallStep(@JsonProperty("callee") String callee) implements RegexStep {
    public String toString() {
        return String.format("(?&%s)", callee);
    }
}
