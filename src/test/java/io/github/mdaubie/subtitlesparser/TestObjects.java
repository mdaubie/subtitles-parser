package io.github.mdaubie.subtitlesparser;

import io.github.mdaubie.subtitlesparser.model.SubRipFile;
import io.github.mdaubie.subtitlesparser.model.SubRipSubtitle;

import java.time.LocalTime;
import java.util.List;

public class TestObjects {
    public static class SubRip {
        private static final SubRipSubtitle sub1 = getSubRipSubtitle(1,
                LocalTime.of(0, 0, 51, 93 * 1000000),
                LocalTime.of(0, 0, 52, 635 * 1000000),
                "(ALARM RINGING)");
        private static final SubRipSubtitle sub2 = getSubRipSubtitle(2,
                LocalTime.of(0, 0, 56, 473 * 1000000),
                LocalTime.of(0, 0, 58, 266 * 1000000),
                "(FOOTSTEPS THUDDING)");
        public static final SubRipFile object1 = getSubRipFile(sub1, sub2);
        public static final String text1 = """
                1
                00:00:51,093 --> 00:00:52,635
                (ALARM RINGING)

                2
                00:00:56,473 --> 00:00:58,266
                (FOOTSTEPS THUDDING)

                """;

        static SubRipFile getSubRipFile(SubRipSubtitle... subtitles) {
            SubRipFile file = new SubRipFile();
            file.subtitles = List.of(subtitles);
            return file;
        }

        static SubRipSubtitle getSubRipSubtitle(int number, LocalTime start, LocalTime end, String content) {
            SubRipSubtitle subRipSubtitle = new SubRipSubtitle();
            subRipSubtitle.number = number;
            subRipSubtitle.start = start;
            subRipSubtitle.end = end;
            subRipSubtitle.content = content;
            return subRipSubtitle;
        }
    }
}
