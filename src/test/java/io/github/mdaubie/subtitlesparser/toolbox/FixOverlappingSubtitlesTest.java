package io.github.mdaubie.subtitlesparser.toolbox;

import io.github.mdaubie.subtitlesparser.TestObjects.*;
import io.github.mdaubie.subtitlesparser.model.Subtitle;
import io.github.mdaubie.subtitlesparser.model.SubtitlesFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.InvalidParameterException;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class FixOverlappingSubtitlesTest {
    @ParameterizedTest
    @MethodSource("overlaps")
    void overlaps(Supplier<Subtitle> first, Supplier<Subtitle> second, boolean expectedResult) {
        assertEquals(FixOverlappingSubtitles.overlaps(first.get(), second.get()), expectedResult);
    }

    static Stream<Arguments> overlaps() {
        return Stream.of(
                Arguments.of(SubRip.sub1, SubRip.sub2, false),
                Arguments.of(SubRip.sub2, SubRip.sub3, true),
                Arguments.of(SubRip.sub2, SubRip.sub3FixedDelay, false)
        );
    }

    @Test()
    void overlapsThrows() {
        assertThrowsExactly(InvalidParameterException.class,
                ()-> FixOverlappingSubtitles.overlaps(SubRip.sub2.get(), SubRip.sub1.get()));
    }

    @ParameterizedTest
    @MethodSource("fixOverlapping")
    void fixOverlapping(FixOverlappingSubtitles.STRATEGY strategy, Supplier<SubtitlesFile> subtitlesFile, Supplier<SubtitlesFile> expectedResult) {
        FixOverlappingSubtitles.fixOverlapping(subtitlesFile.get(), strategy);
        assertThat(subtitlesFile).usingRecursiveComparison().isEqualTo(expectedResult.get());
    }

    static Stream<Arguments> fixOverlapping() {
        return Stream.of(
                Arguments.of(FixOverlappingSubtitles.STRATEGY.DELAY_SECOND, SubRip.object1, SubRip.object1FixedDelay),
                Arguments.of(FixOverlappingSubtitles.STRATEGY.DELAY_SECOND, SubRip.object2, SubRip.object1FixedDelay),
                Arguments.of(FixOverlappingSubtitles.STRATEGY.MERGE, SubRip.object2, SubRip.object2FixedMerge)
        );
    }
}
