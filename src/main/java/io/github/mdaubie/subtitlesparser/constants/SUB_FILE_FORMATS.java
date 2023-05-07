package io.github.mdaubie.subtitlesparser.constants;

import io.github.mdaubie.subtitlesparser.model.Format;
import io.github.mdaubie.subtitlesparser.model.SubRipFile;

/**
 * A collection of the pre-built formats handled by the library
 */
public final class SUB_FILE_FORMATS {
    /**
     * Format for SubRip files
     */
    public static final Format<SubRipFile> SUB_RIP_FORMAT = new Format<>("SubRip", "srt", SubRipFile.class, TIMESTAMP_FORMATS.ISO_8601_COMA.value);
}
