package io.github.mdaubie.subtitlesparser.model;

import java.time.format.DateTimeFormatter;

public record Format<SF extends SubtitlesFile>(String name, String extension,
                                               Class<SF> baseClass,
                                               DateTimeFormatter timestampsFormat) {

    @Override
    public String toString() {
        return String.format("Format %s (.%s)", name, extension);
    }
}
