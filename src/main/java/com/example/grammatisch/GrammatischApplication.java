package com.example.grammatisch;

import com.example.grammatisch.astregex.ASTRegex;
import com.example.grammatisch.astregex.MatchSaveStep;
import com.example.grammatisch.astregex.MatchStep;
import com.example.grammatisch.astregex.PositionSaveStep;
import com.example.grammatisch.grammar.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootConfiguration
@SpringBootApplication
@RestController
public class GrammatischApplication {
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer addCustomBigDecimalDeserialization() {
		return new Jackson2ObjectMapperBuilderCustomizer() {

			@Override
			public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
				jacksonObjectMapperBuilder.annotationIntrospector(new JacksonAnnotationIntrospector() {

					@Override
					public PropertyName findNameForDeserialization(Annotated a) {
						PropertyName nameForDeserialization = super.findNameForDeserialization(a);
						// when @JsonDeserialize is used, USE_DEFAULT is default
						// preventing the implicit constructor to be found
						if (PropertyName.USE_DEFAULT.equals(nameForDeserialization)
								&& a instanceof AnnotatedParameter
								&& ((AnnotatedParameter) a).getDeclaringClass().isRecord()) {
							String str = findImplicitPropertyName((AnnotatedParameter) a);
							if (str != null && !str.isEmpty()) {
								return PropertyName.construct(str);
							}
						}
						return nameForDeserialization;
					}

					@Override
					public String findImplicitPropertyName(AnnotatedMember m) {
						if (m.getDeclaringClass().isRecord()
								&& m instanceof AnnotatedParameter parameter) {
							return m.getDeclaringClass().getRecordComponents()[parameter.getIndex()].getName();
						}
						return super.findImplicitPropertyName(m);
					}
				});
			}

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(GrammatischApplication.class, args);
	}

//	public record Point(List<Integer> arr) {

	// take a grammar, send back a regex
	@GetMapping("/hello")
	public Point hello() {
		var args = new Grammar(List.of(new RuleSpec("Foo", List.of(new Alternative(List.of(new Quoted("'bar'")))))));
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
