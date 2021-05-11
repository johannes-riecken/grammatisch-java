package com.example.grammatisch.astregex;

import org.jetbrains.annotations.NotNull;

public record PositionSaveStep() implements RegexStep {
    public @NotNull String toString() {
        return "(?{ [$^R, pos()] })";
    }
}
