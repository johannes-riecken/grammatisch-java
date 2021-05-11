package com.example.grammatisch.astregex;

import com.example.grammatisch.grammar.Define;

import java.util.List;

public record ASTRegex(List<Define> defines) {
    public String toString() {
        if (defines.size() == 0) {
            throw new AssertionError("ASTRegex must contain at least one Define");
        }
        var buf = new StringBuilder();
        buf.append(String.format("\\A (?&%s) \\z\n(?(DEFINE)\n", defines.get(0).defineName()));
        for (var x : defines) {
            buf.append(x.toString());
            buf.append('\n');
        }
        buf.append(')');
        return buf.toString();
    }
}
