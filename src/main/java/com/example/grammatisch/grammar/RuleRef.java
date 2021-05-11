package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.CallStep;
import com.example.grammatisch.astregex.RegexStep;

public record RuleRef(String ruleRefName) implements Element {

    @Override
    public RegexStep toRegex() {
        return new CallStep(ruleRefName);
    }
}
