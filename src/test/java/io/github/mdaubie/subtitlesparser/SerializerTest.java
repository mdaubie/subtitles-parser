package io.github.mdaubie.subtitlesparser;

import io.github.mdaubie.subtitlesparser.model.Format;
import io.github.mdaubie.subtitlesparser.model.SubtitlesFile;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.github.mdaubie.subtitlesparser.constants.SUB_FILE_FORMATS.SUB_RIP_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializerTest {
    @ParameterizedTest
    @MethodSource("serialize")
    void serialize(Format<? extends SubtitlesFile> format, Supplier<SubtitlesFile> input, String expectedResult) throws Exception {
        String actualResult = new Serializer<>(format).serialize(input.get());
        assertEquals(expectedResult, actualResult);
    }

    public static Stream<Arguments> serialize() {
        return Stream.of(
                Arguments.of(SUB_RIP_FORMAT, TestObjects.SubRip.object1, TestObjects.SubRip.text1)
        );
    }


    @ParameterizedTest
    @MethodSource("patternToStringTemplate")
    void patternToStringTemplate(Pattern pattern, String expectedResult) {
        String actualResult = Serializer.patternToStringTemplate(pattern);
        assertEquals(expectedResult, actualResult);
    }

    public static Stream<Arguments> patternToStringTemplate() {

        return Stream.of(
                Arguments.of("(?<subtitles>.*)", "subtitles"),
                Arguments.of("(?<number>[0-9]+)\\n(?<start>.*?) --> (?<end>.*?)\\n(?<content>.*?)\\n\\n", "number\nstart --> end\ncontent\n\n")
        );
    }
}
