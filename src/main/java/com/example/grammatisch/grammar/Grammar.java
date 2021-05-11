package com.example.grammatisch.grammar;


import com.example.grammatisch.astregex.ASTRegex;

import java.util.ArrayList;
import java.util.List;

public record Grammar(List<RuleSpec> ruleSpecs) {
    public ASTRegex ToRegex() {
        var  defines = new ArrayList<Define>(ruleSpecs.size());
        int i = 0;
        for (var x : ruleSpecs) {
            defines.add(x.ToRegex());
        }
        return new ASTRegex(defines);
    }
}
