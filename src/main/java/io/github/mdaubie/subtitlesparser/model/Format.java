package io.github.mdaubie.subtitlesparser.model;

import io.github.mdaubie.subtitlesparser.constants.TIMESTAMP_FORMATS;

import java.time.format.DateTimeFormatter;

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
