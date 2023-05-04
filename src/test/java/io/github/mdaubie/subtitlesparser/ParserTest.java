package io.github.mdaubie.subtitlesparser;

import io.github.mdaubie.subtitlesparser.model.Format;
import io.github.mdaubie.subtitlesparser.model.SubtitlesFile;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.mdaubie.subtitlesparser.constants.SUB_FILE_FORMATS.SUB_RIP_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;


class ParserTest {
    @ParameterizedTest
    @MethodSource("parse")
    void parse(Format<? extends SubtitlesFile> format, String text, SubtitlesFile expectedResult) throws Exception {
        SubtitlesFile actualResult = new Parser<>(format).parse(text);
        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    public static Stream<Arguments> parse() {
        return Stream.of(
                Arguments.of(SUB_RIP_FORMAT, TestObjects.SubRip.text1, TestObjects.SubRip.object1)
        );
    }
}
