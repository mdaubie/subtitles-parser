package io.github.mdaubie.subtitlesparser;

import io.github.mdaubie.subtitlesparser.model.Format;
import io.github.mdaubie.subtitlesparser.model.PatternedObject;
import io.github.mdaubie.subtitlesparser.model.SubtitlesFile;

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

/**
 * A class to parse the content of a subtitles file into a SubtitlesFile object.
 * The constructor requires a Format object, corresponding to the type of SubtitlesFile to parse.
 * @see Format
 * @param <SF> The type of SubtitlesFile to parse
 */
public record Parser<SF extends SubtitlesFile>(Format<SF> format) {
    /**
     * Parse the provided file into a SubtitlesFile object
     *
     * @param file The file to parse
     * @throws IOException If the provided file is not valid or an I/O error occurs
     */
    @SuppressWarnings("unused")
    public SF parseFile(File file) throws IOException {
        return parse(Files.readString(file.toPath()));
    }

    /**
     * Parse the provided String into a SubtitlesFile object
     *
     * @param text The file content to parse
     * @throws UnexpectedException If the file content is not structured as expected by the defined format
     */
    public SF parse(String text) throws UnexpectedException {
        Matcher matcher = PatternHolder.getPattern(format.baseClass()).matcher(text);
        if (!matcher.matches())
            throw new UnexpectedException(String.format("Parser could not parse %s with format %s", text, format));
        return dynamicParse(matcher, format.baseClass());
    }

    private <T extends PatternedObject> T dynamicParse(Matcher matcher, Class<T> type) throws UnexpectedException {
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
            throw new UnexpectedException("Reflective operation unexpectedly failed", e);
        }
    }

    /**
     * Parse the provided text into an object corresponding to the type of the field specified
     *
     * @param value The text to parse
     * @param field The field in which the parsed object will be injected
     * @return The parsed object
     * @throws UnexpectedException If the field type is not handled by the parser
     */
    //TODO we might want to register some Functions<> to handle the types
    private Object parseObject(String value, Field field) throws UnexpectedException {
        Class<?> type = field.getType();
        if (type == String.class) return value;
        if (type == Integer.class) return Integer.parseInt(value);
        if (type == LocalTime.class) return LocalTime.parse(value, format.timestampsFormat());
        if (type == List.class)
            return parseList(value, ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
        throw new UnexpectedException(String.format("Type %s is not handled by parser", type));
    }

    /**
     * Parse the provided String into a list of the specified type (expected to be a PatternedObject)
     *
     * @param value The text to parse
     * @param type  The type of the list elements
     * @param <T>   The generic type of the lists elements
     * @return The parsed list
     * @throws UnexpectedException If the specified type does not correspond to the generic type of if a parsing exception happens recursively
     */
    private <T extends PatternedObject> List<T> parseList(String value, Type type) throws UnexpectedException {
        //TODO might need to implement basic types handling for some of the formats
        Class<T> elementClass;
        try {
            //noinspection unchecked
            elementClass = (Class<T>) type;
        } catch (ClassCastException e) {
            throw new UnexpectedException(String.format("Provided type %s does not correspond to expected PatternedObject class", type));
        }
        Pattern pattern = PatternHolder.getPattern(elementClass);
        Matcher matcher = pattern.matcher(value);
        List<T> list = new ArrayList<>();
        while (matcher.find())
            list.add(dynamicParse(matcher, elementClass));
        return list;
    }
}
