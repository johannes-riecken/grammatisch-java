package com.example.grammatisch.astregex;

import com.example.grammatisch.grammar.Define;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ASTRegex(@JsonProperty("defines") List<Define> defines) {
    @Override
    public @NotNull String toString() {
        if (defines.isEmpty()) {
            throw new AssertionError("ASTRegex must contain at least one Define");
        }
        var buf = new StringBuilder();
        buf.append(String.format("\\A (?&%s) \\z%n(?(DEFINE)%n", defines.get(0).defineName()));
        for (var x : defines) {
            buf.append(x.toString());
            buf.append('\n');
        }
        buf.append(')');
        return buf.toString();
    }
}
