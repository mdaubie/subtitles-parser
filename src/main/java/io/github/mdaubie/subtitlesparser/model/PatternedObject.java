package io.github.mdaubie.subtitlesparser.model;

import java.util.regex.Pattern;

/**
 * Base class of the model, any component of a subtitles format should extend this class
 */
public abstract class PatternedObject {
    /**
     * @return The pattern with which to parse or serialize the component
     */
    public abstract Pattern getPattern();
}
