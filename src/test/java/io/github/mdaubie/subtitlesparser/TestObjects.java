package io.github.mdaubie.subtitlesparser;

import io.github.mdaubie.subtitlesparser.model.SubRipFile;
import io.github.mdaubie.subtitlesparser.model.SubRipSubtitle;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

//Using supplier to deep copy and keep the objects independent between tests
public class TestObjects {
    public static class SubRip {
        public static final Supplier<SubRipSubtitle> sub1 = () -> getSubRipSubtitle(1,
                LocalTime.of(0, 0, 51, 93 * 1000000),
                LocalTime.of(0, 0, 52, 635 * 1000000),
                "This is the first dialogue");
        public static final Supplier<SubRipSubtitle> sub2 = () -> getSubRipSubtitle(2,
                LocalTime.of(0, 0, 56, 473 * 1000000),
                LocalTime.of(0, 0, 58, 266 * 1000000),
                "This is the second dialogue");
        public static final Supplier<SubRipSubtitle> sub3 = () -> getSubRipSubtitle(3,
                LocalTime.of(0, 0, 57, 908 * 1000000),
                LocalTime.of(0, 0, 59, 701 * 1000000),
                "This is the third dialogue");
        public static final Supplier<SubRipSubtitle> sub3FixedDelay = () -> getSubRipSubtitle(3,
                LocalTime.of(0, 0, 58, 266 * 1000000),
                LocalTime.of(0, 0, 59, 701 * 1000000),
                "This is the third dialogue");
        public static final Supplier<SubRipSubtitle> sub3FixedMerge = () -> getSubRipSubtitle(2,
                LocalTime.of(0, 0, 56, 473 * 1000000),
                LocalTime.of(0, 0, 59, 701 * 1000000),
                "This is the second dialogue"+System.lineSeparator()+"This is the third dialogue");
        public static final Supplier<SubRipFile> object1 = () -> getSubRipFile(sub1.get(), sub2.get(), sub3.get());
        public static final Supplier<SubRipFile> object1FixedDelay = ()->getSubRipFile(sub1.get(), sub2.get(), sub3FixedDelay.get());
        public static final Supplier<SubRipFile> object2 = () -> getSubRipFile(sub2.get(), sub1.get(), sub3.get());
        public static final Supplier<SubRipFile> object2FixedMerge = ()->getSubRipFile(sub1.get(), sub3FixedMerge.get());
        public static final String text1 = """
                1
                00:00:51,093 --> 00:00:52,635
                This is the first dialogue

                2
                00:00:56,473 --> 00:00:58,266
                This is the second dialogue

                3
                00:00:57,908 --> 00:00:59,701
                This is the third dialogue

                """;

        private static SubRipFile getSubRipFile(SubRipSubtitle... subtitles) {
            SubRipFile file = new SubRipFile();
            file.subtitles = new ArrayList<>(List.of(subtitles));
            return file;
        }

        private static SubRipSubtitle getSubRipSubtitle(int number, LocalTime start, LocalTime end, String content) {
            SubRipSubtitle subRipSubtitle = new SubRipSubtitle();
            subRipSubtitle.number = number;
            subRipSubtitle.start = start;
            subRipSubtitle.end = end;
            subRipSubtitle.content = content;
            return subRipSubtitle;
        }
    }
}
