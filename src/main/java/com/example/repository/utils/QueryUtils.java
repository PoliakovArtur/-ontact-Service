package com.example.repository.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.jooq.lambda.Unchecked.biFunction;
import static org.jooq.lambda.Unchecked.predicate;
import static org.jooq.lambda.Unchecked.supplier;

@Component
public class QueryUtils {

    private static final BiFunction<Field, Object, Boolean> CHECKER = biFunction((f, o) -> isNull(f.get(o)));

    public String createUpdateQueryById(String tableName, Object entity) {
        return format("UPDATE %s SET %s WHERE id = ?",
                tableName,
                fieldStream(entity, Set.of("id"))
                        .filter(predicate(f -> {
                            f.setAccessible(true);
                            return f.get(entity) != null && !f.getName().equals("id");
                        }))
                        .map(f -> format("%s = ?", fromCamelCaseToSnakeCase(f.getName())))
                        .collect(Collectors.joining(", "))
        );
    }

    public List<Supplier<Object>> gettersFromNotNullFields(Object entity, Set<String> excludeFields) {
        return fieldStream(entity, excludeFields)
                .<Supplier<Object>>mapMulti(((field, buffer) -> {
                    field.setAccessible(true);
                    if(!CHECKER.apply(field, entity) && !field.getName().equals("id")) {
                        buffer.accept(supplier(() -> field.get(entity)));
                    }
                })).toList();
    }

    public <E> List<Supplier<Object>> gettersFromNotNullFields(E entity) {
        return gettersFromNotNullFields(entity, Collections.emptySet());
    }

    private Stream<Field> fieldStream(Object entity, Set<String> excludeFields) {
        return Stream.of(entity.getClass().getDeclaredFields())
                .filter(f -> excludeFields.contains(f.getName()));
    }

    private String fromCamelCaseToSnakeCase(String string) {
        return string.chars()
                .mapMulti((ch, buffer) -> {
                    if(Character.isUpperCase(ch)) {
                        buffer.accept('_');
                        buffer.accept(Character.toLowerCase(ch));
                    }
                    else buffer.accept(ch);
                })
                .mapToObj(i -> String.valueOf((char) i))
                .reduce(String::concat)
                .orElse("");
    }
}
