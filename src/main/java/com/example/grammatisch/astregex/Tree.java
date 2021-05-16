package com.example.grammatisch.astregex;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record Tree(@JsonProperty("val") List<Integer> val, @JsonProperty("left") Tree left, @JsonProperty("right") Tree right) {
    @NotNull List<List<Integer>> indicesList() {
        if (left == null && right == null) {
            return List.of(val);
        }
        var ret = new ArrayList<List<Integer>>();
        if (left != null) {
            ret.addAll(left.indicesList());
        }
        if (right != null) {
            ret.addAll(right.indicesList());
        }
        return ret;
    }
}
