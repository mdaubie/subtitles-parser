package com.github.mdaubie.srtparser.constants;

import java.time.format.DateTimeFormatter;

public enum TIMESTAMPS_FORMATS {
    ISO_8601_COMA(DateTimeFormatter.ofPattern("HH:mm:ss,SSS")),
    ;

    public final DateTimeFormatter value;

    TIMESTAMPS_FORMATS(DateTimeFormatter value) {
        this.value = value;
    }
}
