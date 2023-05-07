package io.github.mdaubie.subtitlesparser.model;

import java.util.regex.Pattern;

/**
 * A class describing a SubRip subtitle
 */
public class SubRipSubtitle extends Subtitle {
    @Override
    public Pattern getPattern() {
        return Pattern.compile("(?<number>[0-9]+)\\n(?<start>.*?) --> (?<end>.*?)\\n(?<content>.*?)\\n\\n", Pattern.DOTALL);
    }
}
