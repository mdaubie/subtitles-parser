package io.github.mdaubie.subtitlesparser;

import io.github.mdaubie.subtitlesparser.model.Format;
import io.github.mdaubie.subtitlesparser.model.PatternedObject;
import io.github.mdaubie.subtitlesparser.model.SubtitlesFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.rmi.UnexpectedException;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Serializer<SF extends SubtitlesFile>(Format<SF> format) {
    public void writeToFile(SF subtitlesFile, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(serialize(subtitlesFile));
        writer.close();
    }

    public String serialize(SF sf) throws UnexpectedException {
        return dynamicSerialize(sf);
    }

    private String dynamicSerialize(PatternedObject object) throws UnexpectedException {
        String template = patternToStringTemplate(PatternHolder.getPattern(object.getClass()));
        Class<?> type = object.getClass();
        for (Field field : type.getFields())
            template = template.replace(field.getName(), serializeAttribute(object, field));
        return template;
    }

    private String serializeAttribute(PatternedObject patternedObject, Field field) throws UnexpectedException {
        Class<?> type = field.getType();
        Object fieldObject;
        try {
            boolean isAccessible = field.canAccess(patternedObject);
            if (!isAccessible) field.setAccessible(true);
            fieldObject = field.get(patternedObject);
            if (!isAccessible) field.setAccessible(false);
        } catch (IllegalAccessException e) {
            throw new UnexpectedException("reflect library failed to make field accessible", e);
        }
        if (type == String.class) return String.valueOf(fieldObject);
        if (type == Integer.class) return String.valueOf(fieldObject);
        if (type == LocalTime.class) return ((LocalTime) fieldObject).format(format.timestampsFormat());
        if (type == List.class) return serializeList(fieldObject);
        throw new UnexpectedException(String.format("Type %s is not handled by serializer when serializing class %s", type, fieldObject.getClass()));
    }

    private String serializeList(Object object) throws UnexpectedException {
        StringBuilder content = new StringBuilder();
        List<?> list = (List<?>) object;
        for (Object o : list) {
            if (o instanceof PatternedObject)
                content.append(dynamicSerialize((PatternedObject) o));
            else
                //TODO include primitive objects (non-patterned)
                throw new UnexpectedException(String.format("Unexpected type %s found when serializing list for format %s", object.getClass(), format));
        }
        return content.toString();
    }

    // method to transform regex Pattern into corresponding String template, with placeholders to be replaced by actual values
    // TODO we should actually use something similar as the pattern holder, to not reprocess it everytime
    public static String patternToStringTemplate(Pattern pattern) {
        String regex = "(\\(\\?<(?<groupName>.*?)>.*?\\))";
        Matcher matcher = Pattern.compile(regex).matcher(pattern.pattern());
        return matcher.replaceAll("${groupName}").replace("\\n", "\n");
    }
}
