package com.example.grammatisch.astregex;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    @Test
    void emptyTree() {
        var t = new Tree(List.of(), null, null);
        assertEquals(List.of(List.of()), t.indicesList());
    }

    @Test
    void singletonTree() {
        var t = new Tree(List.of(42), null, null);
        assertEquals(List.of(List.of(42)), t.indicesList());
    }

    @Test
    void matchCombineStep() {
        var t0 = MatchCombineStep.unfoldAnnotatedRegexTree(0);
        assertEquals(new Tree(List.of(), null, null), t0);
        var t1 = MatchCombineStep.unfoldAnnotatedRegexTree(1);
        assertEquals(new Tree(null, new Tree(List.of(0), null, null), new Tree(List.of(1), null, null)), t1);
        var t2 = MatchCombineStep.unfoldAnnotatedRegexTree(2);
        assertEquals(new Tree(null, new Tree(null, new Tree(List.of(0, 0), null, null), new Tree(List.of(0, 1), null, null)), new Tree(List.of(1), null, null)), t2);
    }

    @Test
    void indicesList() {
        var t = new Tree(null,
                new Tree(List.of(3),
                        null,
                        null),
                new Tree(null,
                        new Tree(List.of(6), null, null),
                        new Tree(null, new Tree(List.of(8), null, null), new Tree(List.of(9), null, null))));
        assertEquals(List.of(List.of(3), List.of(6), List.of(8), List.of(9)), t.indicesList());
    }
}
