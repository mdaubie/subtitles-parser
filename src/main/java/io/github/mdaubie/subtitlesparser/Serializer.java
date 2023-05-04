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
    public void writeToFile(SubtitlesFile subtitlesFile, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(serialize(subtitlesFile));
        writer.close();
    }

    public String serialize(SubtitlesFile sf) throws UnexpectedException {
        return dynamicSerialize(sf);
    }

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
        if (type == LocalTime.class) return ((LocalTime) field.get(object)).format(format.timestampsFormat());
        if (type == List.class) return serializeList(object, field);
        throw new UnexpectedException(String.format("Type %s is not handled by Serializer", type));
    }

    private String serializeList(Object object, Field field) throws UnexpectedException, IllegalAccessException {
        StringBuilder content = new StringBuilder();
        List<PatternedObject> list = (List<PatternedObject>) field.get(object);
        //TODO include primitive objects (non-patterned)
        for (PatternedObject o : list) content.append(dynamicSerialize(o));
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
