package com.example.grammatisch.astregex;

import org.jetbrains.annotations.NotNull;

public record PositionSaveStep() implements RegexStep {
    @Override
    public @NotNull String toString() {
        return "(?{ [$^R, pos()] })";
    }
}
