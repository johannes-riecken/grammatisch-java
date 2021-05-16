package com.example.grammatisch.grammar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Alternative(@JsonProperty("elements") List<Element> elements) { }
