package io.github.mdaubie.subtitlesparser.model;

import java.time.LocalTime;

/**
 * A base class to describe a subtitle component
 */
public abstract class Subtitle extends PatternedObject {
    /**
     * The index of the subtitle (starting from 1)
     */
    public Integer number;
    /**
     * The timestamp to start displaying the subtitle
     */
    public LocalTime start;
    /**
     * The timestamp to end displaying the subtitle
     */
    public LocalTime end;
    /**
     * The content of the subtitle
     */
    public String content;
}
