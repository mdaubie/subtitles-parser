# Subtitles Parser

[![version](https://img.shields.io/github/v/release/mdaubie/subtitles-parser?display_name=tag)](https://github.com/mdaubie/subtitles-parser/releases/latest)
[![release status](https://github.com/mdaubie/subtitles-parser/actions/workflows/release.yml/badge.svg)](https://github.com/mdaubie/subtitles-parser/actions/workflows/release.yml)
[![checks status](https://github.com/mdaubie/subtitles-parser/actions/workflows/test-all.yml/badge.svg)](https://github.com/mdaubie/subtitles-parser/actions/workflows/test-all.yml)
[![publish status](https://github.com/mdaubie/subtitles-parser/actions/workflows/publish.yml/badge.svg)](https://github.com/mdaubie/subtitles-parser/actions/workflows/publish.yml)
[![license](https://img.shields.io/github/license/mdaubie/subtitles-parser)](https://github.com/mdaubie/subtitles-parser/blob/master/LICENSE)

Library for parsing subtitles files using regexes

### Handled formats

- Currently: SubRip
- Upcoming: MicroDVD, WebVTT, SSA, ASS

### How to use

- Parse your file with the Parser class, you will need the format of the file and its path
- Apply your changes: for example fix the overlapping subtitles in the file with the dedicated tool
- Serialize your file back, you will need the format of the file and the path of the new file

```java
public class Main {
    public static void main(String[] args) throws IOException {
        File mySubtitlesFile = new File("path/myFile");
        SubRipFile parsedFile = new Parser<>(SUB_RIP_FORMAT).parseFile(mySubtitlesFile);
        FixOverlappingSubtitles.fixOverlapping(parsedFile, FixOverlappingSubtitles.STRATEGY.MERGE);
        new Serializer<>(SUB_RIP_FORMAT).writeToFile(parsedFile, new File("path/myFileFixed"));
    }
}
```

### Upcoming features

- New subtitles formats: MicroDVD, WebVTT, SSA, ASS
- New tool: subtitles shifting
- Format conversion: parse a SubRip file and serialize it as an SSA file to customize it for example
- Format auto-detection: might be done simply on file extension, or by pattern recognition

### Related projects

I am working on a web app project to handle my collection of downloaded movies and series, you can find it
here: [The Movie Shelf](https://github.com/mdaubie/movie-shelf)

This is a personal project for my needs, so it probably won't be interesting for you (I might actually keep it private),
but I need to develop some libraries for this main project which might be useful to you:

- [MKV Toolbox](https://github.com/mdaubie/mkv-toolbox)
- [Torrent Name Parser](https://github.com/mdaubie/torrent-name-parser)
- [Color of Film](https://github.com/mdaubie/color-of-film)
