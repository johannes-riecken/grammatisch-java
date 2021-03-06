package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.RegexStep;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Define(@JsonProperty("defineName") String defineName, @JsonProperty("regexSteps") List<RegexStep> regexSteps) {
    @Override
    public @NotNull String toString() {
        if (regexSteps.isEmpty()) {
            throw new AssertionError("Define must contain at least one RegexStep");
        }
        var buf = new StringBuilder();
        buf.append("(?<");
        buf.append(defineName);
        buf.append("> ");
        for (var x : regexSteps) {
            buf.append(x.toString());
            buf.append(' ');
        }
        buf.append(')');
        return buf.toString();
    }
}
