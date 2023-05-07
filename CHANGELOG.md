# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2023-05-07
### :sparkles: New Features
- [`4c877b5`](https://github.com/mdaubie/subtitles-parser/commit/4c877b53f87d239b9f79c5717a4754082aac239d) - add the shift subtitles tool *(commit by [@mdaubie](https://github.com/mdaubie))*

### :recycle: Refactors
- [`37c516c`](https://github.com/mdaubie/subtitles-parser/commit/37c516cc702a2938151b233b5ce0d54706a66f48) - use generic types in the relevant Parser and Serializer methods, as to make their use more strict and safe *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`cfcc9b2`](https://github.com/mdaubie/subtitles-parser/commit/cfcc9b2dc94f600f86843bae75a48129b5732ab8) - suppress impossible IllegalAccessException in attribute serialization and use safe cast in list serialization *(commit by [@mdaubie](https://github.com/mdaubie))*

### :memo: Documentation Changes
- [`93aa85c`](https://github.com/mdaubie/subtitles-parser/commit/93aa85ccb99c4610db2fe1f5783ff0907e571616) - document the serializer and parser classes *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`43fc896`](https://github.com/mdaubie/subtitles-parser/commit/43fc8966268dc4ea23ce9157c1baa9ce6f27fce0) - document the model *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`47a4b7e`](https://github.com/mdaubie/subtitles-parser/commit/47a4b7e3f50aac72bafda605e35d7c246d5121b8) - document the SUB_FILE_FORMATS and the PatternHolder *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`e9726d6`](https://github.com/mdaubie/subtitles-parser/commit/e9726d6446217e99a9796c64012b3623561ab72a) - add Maven Central badge in README *(commit by [@mdaubie](https://github.com/mdaubie))*

### :art: Code Style Changes
- [`3c76b81`](https://github.com/mdaubie/subtitles-parser/commit/3c76b81041fec654c9352173eeb0f5d5f7a57ce5) - reorder and center README header *(commit by [@mdaubie](https://github.com/mdaubie))*


## [1.0.1] - 2023-05-05
### :bug: Bug Fixes
- [`cd18c3e`](https://github.com/mdaubie/subtitles-parser/commit/cd18c3ebe01e3be6903c650f69d6b09c6cada6e6) - **publish**: downgrade nexus-staging-maven-plugin to more stable 1.6.13 version *(commit by [@mdaubie](https://github.com/mdaubie))*


## [1.0.0] - 2023-05-04
### :boom: BREAKING CHANGES
- due to [`8dddb25`](https://github.com/mdaubie/subtitles-parser/commit/8dddb259d2339fab3649a7f7b88a3fc67dcec627) - add a toolbox, with the FixOverlappingSubtitles tool, including 3 fixing strategies *(commit by [@mdaubie](https://github.com/mdaubie))*:

  add a toolbox, with the FixOverlappingSubtitles tool, including 3 fixing strategies


### :sparkles: New Features
- [`7186bf0`](https://github.com/mdaubie/subtitles-parser/commit/7186bf0484e2f5fdc3fe832f0bcff92a736b349e) - add a serializer, taking a subtitle object as input, and returning, depending on the format, the corresponding file content, directly printable in a file *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`8dddb25`](https://github.com/mdaubie/subtitles-parser/commit/8dddb259d2339fab3649a7f7b88a3fc67dcec627) - add a toolbox, with the FixOverlappingSubtitles tool, including 3 fixing strategies *(commit by [@mdaubie](https://github.com/mdaubie))*

### :recycle: Refactors
- [`c27e125`](https://github.com/mdaubie/subtitles-parser/commit/c27e12531570737b636e5b4da016927f9acb8fd3) - **test**: move test objects to holder class, so they can be used by all tests *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`639795b`](https://github.com/mdaubie/subtitles-parser/commit/639795b0942cec4f1cb9ad9566a46a4df8b1f31b) - **test**: encapsulate test objects in suppliers, so that tests use same data but different objects, and do not interfere between them *(commit by [@mdaubie](https://github.com/mdaubie))*

### :white_check_mark: Tests
- [`5c0d1c2`](https://github.com/mdaubie/subtitles-parser/commit/5c0d1c2d8a1029b356d9b7bf23d5c4f694ecc9ce) - add tests on the serializer methods *(commit by [@mdaubie](https://github.com/mdaubie))*

### :memo: Documentation Changes
- [`3e2c556`](https://github.com/mdaubie/subtitles-parser/commit/3e2c5566d7d55d0152f91cc37c9690727a1b6b21) - add How to use section in README *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`2551c38`](https://github.com/mdaubie/subtitles-parser/commit/2551c38708c2b31b43ae0a3a72df01998ab56838) - add Upcoming features section in README *(commit by [@mdaubie](https://github.com/mdaubie))*


## [0.2.0] - 2023-05-04
### :sparkles: New Features
- [`980909c`](https://github.com/mdaubie/subtitles-parser/commit/980909c03a7a4fc88863ce7c7cba5040f49cd741) - add main timestamp formats *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`81a1024`](https://github.com/mdaubie/subtitles-parser/commit/81a1024d48b40574529258a977441d3c0b117ee0) - add a SUB_FILE_FORMATS constant, including SupRip format *(commit by [@mdaubie](https://github.com/mdaubie))*

### :construction_worker: Build System
- [`c9184b8`](https://github.com/mdaubie/subtitles-parser/commit/c9184b8cc444c27ce39e8315aa2d78f22dfbce23) - **publish**: add Maven publishing configuration *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`c0c20c2`](https://github.com/mdaubie/subtitles-parser/commit/c0c20c2a3261b75fb17501520b3c4c69b401cd4e) - **publish**: add publish GitHub workflow *(commit by [@mdaubie](https://github.com/mdaubie))*

### :memo: Documentation Changes
- [`e9ae923`](https://github.com/mdaubie/subtitles-parser/commit/e9ae9230927187f07859b432bca683c0108dd75b) - add missing minimum metada in pom.xml *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`e4f4ca4`](https://github.com/mdaubie/subtitles-parser/commit/e4f4ca44fa59abfc496267e7e7244ffe5bad39f3) - add badges in the README *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`2110d8b`](https://github.com/mdaubie/subtitles-parser/commit/2110d8bdfdfd3b655e3b49546fd0bbadaff6ba21) - add a Related Projects section in README *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`5a26cfd`](https://github.com/mdaubie/subtitles-parser/commit/5a26cfd76dd972de0867b1baf7988e51c3718e40) - add Handled formats section in README *(commit by [@mdaubie](https://github.com/mdaubie))*

### :art: Code Style Changes
- [`3bd4950`](https://github.com/mdaubie/subtitles-parser/commit/3bd495033fb9eaf174ecdb867817db5e68154c3d) - rename tests job to 'Checks' for prettier badge display *(commit by [@mdaubie](https://github.com/mdaubie))*


## [0.1.1] - 2023-05-04
### :recycle: Refactors
- [`28277bd`](https://github.com/mdaubie/subtitles-parser/commit/28277bdd308da11fd4b5a70e84e8f3b4f83b69ea) - rename main package name from io.github.mdaubie.srtparser to io.github.mdaubie.subtitlesparser *(commit by [@mdaubie](https://github.com/mdaubie))*

### :white_check_mark: Tests
- [`64c4019`](https://github.com/mdaubie/subtitles-parser/commit/64c4019587b155794de2a57c35aa0a716d008585) - add maven-surefire-plugin config *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`6eab660`](https://github.com/mdaubie/subtitles-parser/commit/6eab66095f164c039e1551494b23dd5c561e73e0) - add test-all workflow, running all tests with Maven *(commit by [@mdaubie](https://github.com/mdaubie))*
- [`c5f2454`](https://github.com/mdaubie/subtitles-parser/commit/c5f2454ff1f2cac1e6ed172867cdc56a0fbb6313) - add tests check before merging to release and publish as well *(commit by [@mdaubie](https://github.com/mdaubie))*

### :memo: Documentation Changes
- [`8f02109`](https://github.com/mdaubie/subtitles-parser/commit/8f0210929ee9be8fdedf102c092b741a4fe8e2ba) - add MIT license *(commit by [@mdaubie](https://github.com/mdaubie))*


## [0.1.0] - 2023-05-03
### :sparkles: New Features
- [`35fbd94`](https://github.com/mdaubie/subtitles-parser/commit/35fbd947907ae7fcf7b57884e08ea1ae29b64f69) - add model base *(commit by [@mdaubie](https://github.com/mdaubie))*

### :white_check_mark: Tests
- [`de627d5`](https://github.com/mdaubie/subtitles-parser/commit/de627d52eddd8fcc6dfb9c120414cb966b725a4b) - add Parser tests with SubRip format *(commit by [@mdaubie](https://github.com/mdaubie))*

### :construction_worker: Build System
- [`146b6a0`](https://github.com/mdaubie/subtitles-parser/commit/146b6a0ff83798dcce95bb77b6779cee41aced48) - add a release github workflow *(commit by [@mdaubie](https://github.com/mdaubie))*


[0.1.0]: https://github.com/mdaubie/subtitles-parser/compare/0.0.0...0.1.0
[0.1.1]: https://github.com/mdaubie/subtitles-parser/compare/0.1.0...0.1.1
[0.2.0]: https://github.com/mdaubie/subtitles-parser/compare/0.1.1...0.2.0
[1.0.0]: https://github.com/mdaubie/subtitles-parser/compare/0.2.0...1.0.0
[1.0.1]: https://github.com/mdaubie/subtitles-parser/compare/1.0.0...1.0.1
[1.1.0]: https://github.com/mdaubie/subtitles-parser/compare/1.0.1...1.1.0