package com.example.grammatisch.grammar;


import com.example.grammatisch.astregex.ASTRegex;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record Grammar(@JsonProperty("ruleSpecs") List<RuleSpec> ruleSpecs) {
    public @NotNull ASTRegex toRegex() {
        var  defines = new ArrayList<Define>(ruleSpecs.size());
        for (var x : ruleSpecs) {
            defines.add(x.toRegex());
        }
        return new ASTRegex(defines);
    }
}
