package com.example.grammatisch.grammar;

import com.example.grammatisch.Point;
import com.example.grammatisch.astregex.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrammarTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toRegex() {
        {
            var args = new Grammar(List.of(new RuleSpec("Foo", List.of(new Alternative(List.of(new Quoted("'bar'")))))));
            var want = new ASTRegex(List.of(new Define("Foo",
                    List.of(new PositionSaveStep(), new MatchStep("bar"), new MatchSaveStep("Foo")))));
            var got = args.toRegex();
            assertEquals(want, got);
        }
        {
            var args = new Grammar(List.of(new RuleSpec("foo",
                            List.of(new Alternative(List.of(new RuleRef("Bar"), new RuleRef("Bar"))))),
                    new RuleSpec("Bar", List.of(new Alternative(List.of(new Quoted("'baz'")))))));
            var want = new ASTRegex(List.of(
                    new Define("foo", List.of(new CallStep("Bar"), new CallStep("Bar"), new MatchCombineStep("foo", 2))),
                    new Define("Bar", List.of(new PositionSaveStep(), new MatchStep("baz"), new MatchSaveStep("Bar")))
            ));
            var got = args.toRegex();
            assertEquals(want, got);
        }
    }
}