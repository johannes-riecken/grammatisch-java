package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.CallStep;
import com.example.grammatisch.astregex.RegexStep;
import org.jetbrains.annotations.NotNull;

public record RuleRef(String ruleRefName) implements Element {

    @Override
    public @NotNull RegexStep toRegex() {
        return new CallStep(ruleRefName);
    }
}
