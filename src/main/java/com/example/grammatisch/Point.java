package com.example.grammatisch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

public record Point(@JsonProperty("thing") InnerThing thing) {}
class Inner implements InnerThing {
    @Override
    public void greet() {
        System.out.println("Hello from inner");
    }
}

class Inner2 implements InnerThing {

    @Override
    public void greet() {
        System.out.println("Hello from inner2");
    }
}

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
interface InnerThing {
    void greet();
}
//public record Point(List<Integer> arr) {}