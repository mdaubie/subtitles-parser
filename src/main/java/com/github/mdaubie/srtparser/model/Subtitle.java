package com.github.mdaubie.srtparser.model;

import java.time.LocalTime;

public abstract class Subtitle extends PatternedObject {
    public Integer number;
    public LocalTime start;
    public LocalTime end;
    public String content;

    public boolean overlaps(Subtitle other) {
        return (start.compareTo(other.start) <= 0 && other.start.compareTo(end) > 0) ||
                (other.start.compareTo(start) >= 0 && start.compareTo(other.end) < 0);
    }
}
