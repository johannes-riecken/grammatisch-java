package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.CallStep;
import com.example.grammatisch.astregex.RegexStep;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record RuleRef(@JsonProperty("ruleRefName") String ruleRefName) implements Element {

    @Override
    public @NotNull RegexStep toRegex() {
        return new CallStep(ruleRefName);
    }
}
