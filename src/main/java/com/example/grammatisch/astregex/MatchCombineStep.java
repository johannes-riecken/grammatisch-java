package com.example.grammatisch.astregex;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public record MatchCombineStep(@JsonProperty("combineRuleName") String combineRuleName, @JsonProperty("depth") int depth) implements RegexStep {
    static @Nullable Tree unfoldAnnotatedRegexTree(int n) {
        var bottomLeft = new ArrayList<Integer>();
        for (var i = 0; i < n; i++) {
            bottomLeft.add(0);
        }
        List<Integer> right = new ArrayList<>(bottomLeft);
        if (!right.isEmpty())
            right.set(right.size() - 1, 1);
        var res = new Tree(bottomLeft, null, null);

        for (var i = 0; i < n; i++) {
            res = new Tree(null, res, new Tree(right, null, null));
            right = right.subList(1, right.size());
        }
        return res;
    }
    static @NotNull String indexOfR(@NotNull List<Integer> indexes) {
        var buf = new StringBuilder();
        buf.append("$^R->[");
        var i = 0;
        for (var x : indexes) {
            buf.append(x);
            if (i++ != indexes.size() - 1)
                buf.append("][");
        }
        buf.append("]");
        return buf.toString();
    }

    @Override
    public String toString() {
        var beginIdx = 1;
        var endIdx = 2;
        var arr = unfoldAnnotatedRegexTree(depth).indicesList();
        var i0 = indexOfR(arr.get(0));
        var i1Param = new ArrayList<Integer>(arr.get(1));
        i1Param.add(beginIdx);
        var i1 = indexOfR(i1Param);
        var i2Param = new ArrayList<Integer>(arr.get(arr.size() - 1));
        i2Param.add(endIdx);
        var i2 = indexOfR(i2Param);
        var children = new StringBuilder();
        var i = 0;
        for (var x : arr.subList(1, arr.size())) {
            children.append(indexOfR(x));
            if (i < arr.size() - 2) {
                children.append(", ");
            }
            i++;
        }
        return String.format("(?{ [%s, ['%s', %s, %s, [%s]]] })", i0, combineRuleName, i1, i2, children);
    }
}
