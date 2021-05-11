package com.example.grammatisch.astregex;

import java.util.ArrayList;
import java.util.List;

public record MatchCombineStep(String combineRuleName, int depth) implements RegexStep {
    static Tree unfoldAnnotatedRegexTree(int n) {
        var bottomLeft = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            bottomLeft.add(0);
        }
        var res = new Tree(bottomLeft, null, null);
        for (int i = 0; i < n; i++) {
            var val = res.val();
            val = val.subList(0, -2);
            var right = new ArrayList<>(val);
            right.add(1);
            res = new Tree(val, res, new Tree(right, null, null));
        }
        return res;
    }
    static String indexOfR(List<Integer> indexes) {
        var buf = new StringBuilder();
        buf.append("$^R->[");
        int i = 0;
        for (var x : indexes) {
            buf.append(x);
            if (i != indexes.size() - 1)
                buf.append("][");
            i++;
        }
        buf.append("]");
        return buf.toString();
    }

    public String toString() {
        var beginIdx = 1;
        var endIdx = 2;
        var arr = unfoldAnnotatedRegexTree(depth).indicesList();
        var i0 = indexOfR(arr.get(0));
        var i1Param = arr.get(1);
        i1Param.add(beginIdx);
        var i1 = indexOfR(i1Param);
        var i2Param = arr.get(arr.size() - 1);
        i2Param.add(endIdx);
        var i2 = indexOfR(i2Param);
        var children = new StringBuilder();
        int i = 0;
        for (var x : arr.subList(1, arr.size())) {
            children.append(indexOfR(x));
            if (i < arr.size() - 2) {
                children.append(", ");
            }
            i++;
        }
        return String.format("(?{ [%s, ['%s', %s, %s, [%s]]] })", i0, combineRuleName, i1, i2, children.toString());
    }
}
