package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.*;

import java.util.ArrayList;
import java.util.List;

record RuleSpec(String ruleRef, List<Alternative> alternatives) {
    public Define ToRegex() {
        var ret = new Define(ruleRef, new ArrayList<>(alternatives.get(0).elements().size()));
        for (var x : alternatives.get(0).elements()) {
            ret.regexSteps().add(x.toRegex());
        }
        var regexSteps = altToRegexPost(ruleRef, ret.regexSteps());
        return new Define(ruleRef, regexSteps);
    }
    List<RegexStep> altToRegexPost(String name, List<RegexStep> regexSteps) {
        return appendConstructorStep(name, insertPosSaveStep(isToken(name), regexSteps));
    }
    List<RegexStep> appendConstructorStep(String name, List<RegexStep> regexSteps) {
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
    boolean isToken(String s) {
        if (s.equals("")) {
            throw new AssertionError("empty string passed to isToken");
        }
        var chars = s.toCharArray();
        return chars[0] >= 'A' && chars[0] <= 'Z';
    }
    List<RegexStep> insertPosSaveStep(boolean isToken, List<RegexStep> regexSteps) {
        if (isToken) {
            List<RegexStep> ret = new ArrayList<>(List.of(new PositionSaveStep()));
            ret.addAll(regexSteps);
            return ret;
        }
        return regexSteps;
    }
}