package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.RegexStep;
import org.jetbrains.annotations.NotNull;

public interface Element {
    @NotNull RegexStep toRegex();
}
