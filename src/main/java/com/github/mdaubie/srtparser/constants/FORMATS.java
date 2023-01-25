package com.github.mdaubie.srtparser.constants;

import com.github.mdaubie.srtparser.model.SubRipFile;
import com.github.mdaubie.srtparser.model.SubtitlesFile;

import java.time.format.DateTimeFormatter;

public enum FORMATS {
    SubRip("SubRip", "srt", SubRipFile.class, TIMESTAMPS_FORMATS.ISO_8601_COMA),
    ;

    public final String name;
    public final String extension;
    public final Class<? extends SubtitlesFile> baseClass;
    private final TIMESTAMPS_FORMATS timestampsFormat;

    FORMATS(String name, String extension, Class<? extends SubtitlesFile> baseClass, TIMESTAMPS_FORMATS timestampsFormat) {
        this.name = name;
        this.extension = extension;
        this.baseClass = baseClass;
        this.timestampsFormat = timestampsFormat;
    }

    @Override
    public String toString() {
        return String.format("Format %s (.%s)", name, extension);
    }

    //TODO rename ?
    public DateTimeFormatter timestampPattern() {
        return timestampsFormat.value;
    }

}
