package com.github.mdaubie.srtparser;

import com.github.mdaubie.srtparser.model.Subtitle;
import com.github.mdaubie.srtparser.model.SubtitlesFile;

import java.util.List;

public interface Toolbox {
    static void fixOverlapping(SubtitlesFile sf) {
        //TODO the subtitles number should be shifted too
        List<? extends Subtitle> subtitles = sf.getSubtitles();
        for (int i = 0; i < subtitles.size() - 1; i++) {
            Subtitle first = subtitles.get(i);
            Subtitle second = subtitles.get(i + 1);
            if (second.overlaps(first)) {
                first.start = (first.start.compareTo(second.start) < 0) ? first.start : second.start;
                first.end = (first.end.compareTo(second.end) > 0) ? first.end : second.end;
                first.content = first.content + System.lineSeparator() + second.content;
                subtitles.remove(i + 1);
                i--;
            }
        }
    }

    static void shiftSubtitles(SubtitlesFile sf, long millis) {
        long nanos = millis * 1000000;
        for (int i = 0; i < sf.getSubtitles().size() - 1; i++) {
            Subtitle first = sf.getSubtitles().get(i);
            first.start = first.start.plusNanos(nanos);
            first.end = first.end.plusNanos(nanos);
        }
    }
}
