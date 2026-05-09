package com.example.grammatisch;

import com.example.grammatisch.astregex.ASTRegex;
import com.example.grammatisch.astregex.MatchSaveStep;
import com.example.grammatisch.astregex.MatchStep;
import com.example.grammatisch.astregex.PositionSaveStep;
import com.example.grammatisch.grammar.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.PropertyName;
import tools.jackson.databind.introspect.Annotated;
import tools.jackson.databind.introspect.AnnotatedMember;
import tools.jackson.databind.introspect.AnnotatedParameter;
import tools.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootConfiguration
@SpringBootApplication
@RestController
public class GrammatischApplication {
	public static void main(String[] args) {
		SpringApplication.run(GrammatischApplication.class, args);
	}

//	public record Point(List<Integer> arr) {

	// take a grammar, send back a regex
	@GetMapping("/hello")
	public Point hello(
			@RequestParam(name="ruleRef", required = false, defaultValue = "Foo") String ruleRef
			, @RequestParam(name="element", required = false) List<Element> elements
	) {
		if (elements == null) {
			elements = List.of(new Quoted("'bar'"));
		}
		var args = new Grammar(List.of(new RuleSpec(ruleRef, List.of(new Alternative(elements)))));
		var got = args.toRegex();
//		return new Point(List.of(42, 7));
		return new Point(new Inner());
//		Point p = new Point();
//		p.arr = List.of("42", "6");
//		return p;
//		return new Point(42, 7);
	}

	@PostMapping("/hello")
	public String postHello(@RequestBody Point point) {
//		var args = new Grammar(List.of(new RuleSpec("Foo", List.of(new Alternative(List.of(new Quoted("'bar'")))))));
		// {"ruleSpecs":[{"ruleRef":"Foo","alternatives":[{"elements":[{"quoted":"'bar'"}]]]}
		return point.toString();
	}
}
