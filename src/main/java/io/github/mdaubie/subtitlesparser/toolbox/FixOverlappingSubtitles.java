package io.github.mdaubie.subtitlesparser.toolbox;

import io.github.mdaubie.subtitlesparser.model.Subtitle;
import io.github.mdaubie.subtitlesparser.model.SubtitlesFile;

import java.security.InvalidParameterException;
import java.util.List;

public class FixOverlappingSubtitles {
    /**
     * The available strategies
     */
    public enum STRATEGY {
        /**
         * Merges two consecutive subtitles if they are overlapping
         */
        MERGE,
        /**
         * Shorten the length of the first subtitle of two consecutive subtitles if they are overlapping
         */
        SHORTEN_FIRST,
        /**
         * Delay the start of the second subtitle of two consecutive subtitles if they are overlapping
         */
        DELAY_SECOND,
    }

    /**
     * Fix overlapping subtitles in SubtitlesFile object
     *
     * @param sf       The SubtitleFile object for which to fix the subtitles
     * @param strategy The strategy with which to fix the subtitles
     */
    public static void fixOverlapping(SubtitlesFile sf, STRATEGY strategy) {
        //TODO the subtitles number should be shifted too
        List<? extends Subtitle> subtitles = sf.getSubtitles();
        subtitles.get(0).number = 1;
        for (int i = 0; i < subtitles.size() - 1; i++) {
            Subtitle current = subtitles.get(i);
            Subtitle next = subtitles.get(i + 1);
            //TODO test this, is the List updated properly ?
            if (current.start.compareTo(next.start) > 0) {
                Subtitle temp = current;
                current = next;
                next = temp;
            }
            if (overlaps(current, next)) {
                switch (strategy) {
                    case SHORTEN_FIRST -> current.end = next.start;
                    case DELAY_SECOND -> next.start = current.end;
                    case MERGE -> {
                        current.end = next.end;
                        current.content = current.content + System.lineSeparator() + next.content;
                        subtitles.remove(i + 1);
                        next = subtitles.get(i + 1);
                        i--;
                    }
                    default -> throw new InvalidParameterException("Invalid strategy provided");
                }
            }
            if (current.number != next.number + 1)
                next.number = current.number + 1;
        }
    }

    /**
     * Check that the two subtitles provided do not overlap
     *
     * @param first  first Subtitle, is expected to be prior to the second
     * @param second second Subtitle, is expected to be later than the first
     * @return boolean: whether the subtitles overlap or not
     */
    protected static boolean overlaps(Subtitle first, Subtitle second) {
        if (first.start.compareTo(second.start) > 0)
            throw new InvalidParameterException("first Subtitle should be prior to second Subtitle");
        return second.start.compareTo(first.end) < 0;
    }
}
