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

/**
 * A class to serialize a SubtitlesFile object into a text, ready to be written to a file.
 * The constructor requires a Format object, corresponding to the type of SubtitlesFile to serialize.
 * @see Format
 * @param <SF> The type of SubtitlesFile to serialize
 */
public record Serializer<SF extends SubtitlesFile>(Format<SF> format) {
    /**
     * Serialize the provided SubtitlesFile and write it to the provided File
     *
     * @param subtitlesFile Subtitles file to serialize
     * @param file          File to which to write the serialized result
     * @throws IOException If the provided file is not valid or an I/O error occurs
     */
    @SuppressWarnings("unused")
    public void writeToFile(SF subtitlesFile, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(serialize(subtitlesFile));
        writer.close();
    }

    /**
     * Serialize a SubtitlesFile object into a ready-to-write String
     *
     * @param sf The subtitles file object to serialize
     * @return The serialized file, ready to be written to a file
     * @throws UnexpectedException If
     */
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

    /**
     * Serialize the specified field of a given patterned object
     *
     * @param patternedObject The object being serialized
     * @param field           The field to serialize
     * @return The serialized field
     * @throws UnexpectedException If the type of the field is not supported by the serializer (can happen if you are using custom patterned objects)
     */
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

    /**
     * Cast the provided object into a list and serialize its elements (for now only list of PatternedObject are supported)
     *
     * @param object The object to serialize as a list
     * @return The serialized object
     * @throws UnexpectedException If the type of the list elements is not PatternedObject
     */
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

    /**
     * Reverse engineer a regex Pattern into its corresponding String template, with placeholders to be replaced by actual values
     *
     * @param pattern The pattern to convert into a template
     * @return The template
     */
    // TODO we should actually use something similar as the pattern holder, to not reprocess it everytime
    public static String patternToStringTemplate(Pattern pattern) {
        String regex = "(\\(\\?<(?<groupName>.*?)>.*?\\))";
        Matcher matcher = Pattern.compile(regex).matcher(pattern.pattern());
        return matcher.replaceAll("${groupName}").replace("\\n", "\n");
    }
}
