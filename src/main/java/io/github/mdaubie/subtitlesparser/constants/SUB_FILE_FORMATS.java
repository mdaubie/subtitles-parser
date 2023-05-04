package io.github.mdaubie.subtitlesparser.constants;

import io.github.mdaubie.subtitlesparser.model.Format;
import io.github.mdaubie.subtitlesparser.model.SubRipFile;

public final class SUB_FILE_FORMATS {
    public static final Format<SubRipFile> SUB_RIP_FORMAT = new Format<>("SubRip", "srt", SubRipFile.class, TIMESTAMP_FORMATS.ISO_8601_COMA.value);
}
