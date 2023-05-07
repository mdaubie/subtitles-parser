package io.github.mdaubie.subtitlesparser.model;

import io.github.mdaubie.subtitlesparser.constants.TIMESTAMP_FORMATS;

import java.time.format.DateTimeFormatter;

/**
 * An object describing a subtitles format and providing the rules for it to be parsed and serialized within the library
 * @param <SF> The base class of the format
 * @see io.github.mdaubie.subtitlesparser.constants.SUB_FILE_FORMATS
 */
public record Format<SF extends SubtitlesFile>(String name, String extension,
                                               Class<SF> baseClass,
                                               DateTimeFormatter timestampsFormat) {

    public Format(String name, String extension, Class<SF> baseClass, TIMESTAMP_FORMATS timestampsFormat) {
        this(name, extension, baseClass, timestampsFormat.value);
    }

    @Override
    public String toString() {
        return String.format("Format %s (.%s)", name, extension);
    }
}
