package io.github.mdaubie.subtitlesparser.toolbox;

import io.github.mdaubie.subtitlesparser.model.Subtitle;
import io.github.mdaubie.subtitlesparser.model.SubtitlesFile;

import java.security.InvalidParameterException;

public class ShiftSubtitles {
    /**
     * Shifts the subtitle timestamps by a given offset
     *
     * @param sf           The SubtitleFile object for which to shift the subtitles
     * @param millisOffset The offset, from which the subtitles are shifted, can be negative
     * @throws InvalidParameterException if the offset provided is negative and larger than the first timestamp (resulting in a negative timestamp)
     */
    static void shift(SubtitlesFile sf, long millisOffset) {
        long nanosOffset = millisOffset * 1000000;
        if (millisOffset < 0 && sf.getSubtitles().get(0).start.toNanoOfDay() + nanosOffset < 0)
            throw new InvalidParameterException("Too large negative offset provided");
        for (int i = 0; i < sf.getSubtitles().size() - 1; i++) {
            Subtitle first = sf.getSubtitles().get(i);
            first.start = first.start.plusNanos(nanosOffset);
            first.end = first.end.plusNanos(nanosOffset);
        }
    }
}
