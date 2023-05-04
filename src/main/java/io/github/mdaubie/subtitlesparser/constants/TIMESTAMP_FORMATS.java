package io.github.mdaubie.subtitlesparser.constants;

import java.time.format.DateTimeFormatter;

public enum TIMESTAMP_FORMATS {
    ISO_8601_COMA(DateTimeFormatter.ofPattern("HH:mm:ss,SSS")),
    ISO_8601_DOT(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")),
    ;

    public final DateTimeFormatter value;

    TIMESTAMP_FORMATS(DateTimeFormatter value) {
        this.value = value;
    }
}
