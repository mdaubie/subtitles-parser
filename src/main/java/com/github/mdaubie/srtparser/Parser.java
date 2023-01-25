package com.github.mdaubie.srtparser;

import com.github.mdaubie.srtparser.constants.FORMATS;
import com.github.mdaubie.srtparser.model.PatternedObject;
import com.github.mdaubie.srtparser.model.SubtitlesFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

public record Parser(FORMATS format) {
    public <T extends SubtitlesFile> T parseFile(File file) throws IOException {
        return parse(Files.readString(file.toPath()));
    }

    public <T extends SubtitlesFile> T parse(String text) throws UnexpectedException {
        Class<T> elementClass = (Class<T>) format.baseClass;
        Matcher matcher = PatternHolder.getPattern(elementClass).matcher(text);
        if (!matcher.matches())
            throw new UnexpectedException(String.format("Parser could not parse %s with format %s", text, format));
        return dynamicParse(matcher, elementClass);
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

    private Object parseObject(String value, Field field) throws UnexpectedException {
        Class<?> type = field.getType();
        if (type == String.class) return value;
        if (type == Integer.class) return Integer.parseInt(value);
        if (type == LocalTime.class) return LocalTime.parse(value, format.timestampPattern());
        if (type == List.class) return parseList(value, field);
        throw new UnexpectedException(String.format("Type %s is not handled by Parser", type));
    }

    private List<Object> parseList(String value, Field field) throws UnexpectedException {
        Type elementType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        //TODO might need to implement basic types handling
        Class<? extends PatternedObject> elementClass = (Class<? extends PatternedObject>) elementType;
        Pattern pattern = PatternHolder.getPattern(elementClass);
        Matcher matcher = pattern.matcher(value);
        List<Object> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(dynamicParse(matcher, elementClass));
        }
        return list;
    }

    public void writeToFile(SubtitlesFile subtitlesFile, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(deserialize(subtitlesFile));
        writer.close();
    }

    public String deserialize(SubtitlesFile sf) throws UnexpectedException {
        return dynamicSerialize(sf);
    }

    /* TODO remove template args and fetch from format */
    private String dynamicSerialize(PatternedObject object) throws UnexpectedException {
        String template = patternToStringTemplate(PatternHolder.getPattern(object.getClass()));
        try {
            Class<?> type = object.getClass();
            for (Field field : type.getFields()) {
                boolean isAccessible = field.canAccess(object);
                if (!isAccessible) field.setAccessible(true);
                template = template.replace(field.getName(), serializeAttribute(object, field));
                if (!isAccessible) field.setAccessible(false);
            }
            return template;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String serializeAttribute(Object object, Field field) throws UnexpectedException, IllegalAccessException {
        Class<?> type = field.getType();
        if (type == String.class) return String.valueOf(field.get(object));
        if (type == Integer.class) return String.valueOf(field.get(object));
        if (type == LocalTime.class) return ((LocalTime) field.get(object)).format(format.timestampPattern());
        if (type == List.class) return serializeList(object, field);
        throw new UnexpectedException(String.format("Type %s is not handled by Serializer", type));
    }

    private String serializeList(Object object, Field field) throws UnexpectedException, IllegalAccessException {
        StringBuilder content = new StringBuilder();
        List<PatternedObject> list = (List<PatternedObject>) field.get(object);
        //TODO include primitive objects (non patterned)
        for (PatternedObject o : list) content.append(dynamicSerialize(o));
        return content.toString();
    }

    // method to transform regex Pattern into corresponding String template, with placeholders to be replaced by actual values
    public static String patternToStringTemplate(Pattern pattern) {
        String regex = "(\\(\\?<(?<groupName>.*?)>.*?\\))";
        Matcher matcher = Pattern.compile(regex).matcher(pattern.pattern());
        return matcher.replaceAll("${groupName}").replace("\\n", "\n");
    }
}
