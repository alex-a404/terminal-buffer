## Terminal buffer
This is a terminal buffer data structure, which maintains a 'screen' component (in implementation: visible text) and a 'scrollback' component (off-screen), as a queue. 
It tracks a cursor, and allows in the implementation to add/insert text, read text, clear screen/scrollback and set stylistic attributes. A data structure of this type
can be implemented into a terminal emulator.

## Design specifications
Written in Java, the main component is the `TerminalBuffer` class, which maintains `TerminalLine`s on screen and scrollback(as deque). Each `TerminalLine` has a list
of `TerminalCell`s which store one char each and maintain attributes, all of which are defined in `TerminalAttributes` record. This high-level approach allows flexible
implementations which would call methods like `write`, `insert`, `moveCursor`, depending on I/O requirements.

## Build instructions:

`./gradlew build`
