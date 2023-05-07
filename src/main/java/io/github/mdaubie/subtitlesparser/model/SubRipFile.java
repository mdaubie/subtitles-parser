package io.github.mdaubie.subtitlesparser.model;

import java.util.List;
import java.util.regex.Pattern;

/**
 * A class describing SubRip subtitles file
 */
public class SubRipFile extends SubtitlesFile {
    public List<SubRipSubtitle> subtitles;

    @Override
    public Pattern getPattern() {
        return Pattern.compile("(?<subtitles>.*)", Pattern.DOTALL);
    }

    @Override
    public List<SubRipSubtitle> getSubtitles() {
        return subtitles;
    }
}
