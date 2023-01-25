package com.github.mdaubie.srtparser;

import com.github.mdaubie.srtparser.constants.FORMATS;
import com.github.mdaubie.srtparser.model.SubRipFile;
import com.github.mdaubie.srtparser.model.SubRipSubtitle;
import com.github.mdaubie.srtparser.model.SubtitlesFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Stream;


class ParserTest {
    @ParameterizedTest
    @MethodSource("parse")
    void parse(String text, FORMATS format, SubtitlesFile result) throws Exception {
        SubtitlesFile actualResult = new Parser(format).parse(text);
        Assertions.assertThat(actualResult)
                .usingRecursiveComparison()
                .isEqualTo(result);
    }

    public static Stream<Arguments> parse() {
        String fileContent = """
                1
                00:00:51,093 --> 00:00:52,635
                (ALARM RINGING)

                2
                00:00:56,473 --> 00:00:58,266
                (FOOTSTEPS THUDDING)

                """;
        SubRipFile sf = new SubRipFile();
        sf.subtitles = new ArrayList<>() {{
            add(getSubRipSubtitle(1,
                    LocalTime.of(0, 0, 51, 93 * 1000000),
                    LocalTime.of(0, 0, 52, 635 * 1000000),
                    "(ALARM RINGING)"));
            add(getSubRipSubtitle(2,
                    LocalTime.of(0, 0, 56, 473 * 1000000),
                    LocalTime.of(0, 0, 58, 266 * 1000000),
                    "(FOOTSTEPS THUDDING)"));
        }};
        return Stream.of(
                Arguments.of(fileContent, FORMATS.SubRip, sf)
        );
    }

    private static SubRipSubtitle getSubRipSubtitle(int number, LocalTime start, LocalTime end, String content) {
        SubRipSubtitle subRipSubtitle = new SubRipSubtitle();
        subRipSubtitle.number = number;
        subRipSubtitle.start = start;
        subRipSubtitle.end = end;
        subRipSubtitle.content = content;
        return subRipSubtitle;
    }

    //TODO
    @Test
    void deserialize() {
    }

    //TODO
    @Test
    void patternToStringTemplate() {
    }
}
