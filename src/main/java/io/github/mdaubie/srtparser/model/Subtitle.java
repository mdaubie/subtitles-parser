package io.github.mdaubie.srtparser.model;

import java.time.LocalTime;

public abstract class Subtitle extends PatternedObject {
    public Integer number;
    public LocalTime start;
    public LocalTime end;
    public String content;
}
