package com.example.grammatisch.astregex;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MatchSaveStep(@JsonProperty("saveRuleName") String saveRuleName) implements RegexStep {
}
