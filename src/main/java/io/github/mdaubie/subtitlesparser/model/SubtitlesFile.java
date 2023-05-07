package io.github.mdaubie.subtitlesparser.model;

import java.util.List;

/**
 * A base class to describe a subtitles file
 * @see Format
 */
public abstract class SubtitlesFile extends PatternedObject {
    /**
     * @return The list of subtitles of the file
     */
    public abstract List<? extends Subtitle> getSubtitles();
}
