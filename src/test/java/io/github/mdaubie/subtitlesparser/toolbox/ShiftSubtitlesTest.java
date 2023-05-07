package io.github.mdaubie.subtitlesparser.toolbox;

import io.github.mdaubie.subtitlesparser.TestObjects;
import io.github.mdaubie.subtitlesparser.model.SubtitlesFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.InvalidParameterException;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class ShiftSubtitlesTest {
    @ParameterizedTest
    @MethodSource("shift")
    void shift(Supplier<SubtitlesFile> sf, long millisOffset, Supplier<SubtitlesFile> expectedResult) {
        ShiftSubtitles.shift(sf.get(), millisOffset);
        assertThat(sf).usingRecursiveComparison().isEqualTo(expectedResult.get());
    }

    static Stream<Arguments> shift() {
        return Stream.of(
                Arguments.of(TestObjects.SubRip.object1, 1000, TestObjects.SubRip.object1Shifted1000)
        );
    }

    @Test()
    void shiftThrows() {
        assertThrowsExactly(InvalidParameterException.class,
                ()-> ShiftSubtitles.shift(TestObjects.SubRip.object1.get(),-52000));
    }
}
