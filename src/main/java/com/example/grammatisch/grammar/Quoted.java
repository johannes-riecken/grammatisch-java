package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.MatchStep;
import com.example.grammatisch.astregex.RegexStep;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public record Quoted(String quoted) implements Element {
    @Override
    public @NotNull RegexStep toRegex() {
        return new MatchStep(unquote(quoted));
    }

    static @NotNull String unquote(@NotNull String s) {
        return new String(Arrays.copyOfRange(s.toCharArray(), 1, s.length() - 1));
    }
}
