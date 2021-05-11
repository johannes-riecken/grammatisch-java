package com.example.grammatisch.astregex;

import java.util.ArrayList;
import java.util.List;

public record Tree(List<Integer> val, Tree left, Tree right) {
    List<List<Integer>> indicesList() {
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
