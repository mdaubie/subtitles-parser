package io.github.mdaubie.srtparser;

import io.github.mdaubie.srtparser.model.Format;
import io.github.mdaubie.srtparser.model.PatternedObject;
import io.github.mdaubie.srtparser.model.SubtitlesFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.rmi.UnexpectedException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Parser<SF extends SubtitlesFile>(Format<SF> format) {

    public SF parseFile(File file) throws IOException {
        return parse(Files.readString(file.toPath()));
    }

    public SF parse(String text) throws UnexpectedException {
        Matcher matcher = PatternHolder.getPattern(format.baseClass()).matcher(text);
        if (!matcher.matches())
            throw new UnexpectedException(String.format("Parser could not parse %s with format %s", text, format));
        return dynamicParse(matcher, format.baseClass());
    }

    private <T> T dynamicParse(Matcher matcher, Class<T> type) throws UnexpectedException {
        try {
            T object = type.getConstructor().newInstance();
            for (Field field : type.getFields()) {
                boolean isAccessible = field.canAccess(object);
                if (!isAccessible) field.setAccessible(true);
                String value = matcher.group(field.getName());
                field.set(object, parseObject(value, field));
                if (!isAccessible) field.setAccessible(false);
            }
            return object;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO we might want to register some Functions<> to handle the types
    private Object parseObject(String value, Field field) throws UnexpectedException {
        Class<?> type = field.getType();
        if (type == String.class) return value;
        if (type == Integer.class) return Integer.parseInt(value);
        if (type == LocalTime.class) return LocalTime.parse(value, format.timestampsFormat());
        if (type == List.class) return parseList(value, (ParameterizedType) field.getGenericType());
        throw new UnexpectedException(String.format("Type %s is not handled by parser", type));
    }

    private List<Object> parseList(String value, ParameterizedType type) throws UnexpectedException {
        Type elementType = type.getActualTypeArguments()[0];
        //TODO might need to implement basic types handling for some of the formats
        Class<? extends PatternedObject> elementClass = (Class<? extends PatternedObject>) elementType;
        Pattern pattern = PatternHolder.getPattern(elementClass);
        Matcher matcher = pattern.matcher(value);
        List<Object> list = new ArrayList<>();
        while (matcher.find())
            list.add(dynamicParse(matcher, elementClass));
        return list;
    }
}
