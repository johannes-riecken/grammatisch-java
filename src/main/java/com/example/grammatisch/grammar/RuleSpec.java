package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record RuleSpec(@JsonProperty("ruleRef") String ruleRef, @JsonProperty("alternatives") List<Alternative> alternatives) {
    public @NotNull Define toRegex() {
        var ret = new Define(ruleRef, new ArrayList<>(alternatives.get(0).elements().size()));
        for (var x : alternatives.get(0).elements()) {
            ret.regexSteps().add(x.toRegex());
        }
        var regexSteps = altToRegexPost(ruleRef, ret.regexSteps());
        return new Define(ruleRef, regexSteps);
    }
    @NotNull List<RegexStep> altToRegexPost(@NotNull String name, @NotNull List<RegexStep> regexSteps) {
        return appendConstructorStep(name, insertPosSaveStep(isToken(name), regexSteps));
    }
    @NotNull List<RegexStep> appendConstructorStep(String name, @NotNull List<RegexStep> regexSteps) {
        var callStepCount = 0;
        for (var x : regexSteps) {
            if (x instanceof CallStep) {
                callStepCount++;
            }
        }
        if (callStepCount > 0) {
            regexSteps.add(new MatchCombineStep(name, callStepCount));
        } else {
            regexSteps.add(new MatchSaveStep(name));
        }
        return regexSteps;
    }
    boolean isToken(@NotNull String s) {
        if (s.equals("")) {
            throw new AssertionError("empty string passed to isToken");
        }
        var chars = s.toCharArray();
        return chars[0] >= 'A' && chars[0] <= 'Z';
    }
    @NotNull List<RegexStep> insertPosSaveStep(boolean isToken, @NotNull List<RegexStep> regexSteps) {
        if (isToken) {
            List<RegexStep> ret = new ArrayList<>(List.of(new PositionSaveStep()));
            ret.addAll(regexSteps);
            return ret;
        }
        return regexSteps;
    }
}
