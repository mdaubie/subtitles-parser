package com.github.mdaubie.srtparser.model;

import java.util.List;

public abstract class SubtitlesFile extends PatternedObject {
    public abstract List<? extends Subtitle> getSubtitles();
}
