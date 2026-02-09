This is a terminal buffer data structure, which maintains a 'screen' component (in implementation: visible text) and a 'scrollback' component (off-screen), as a queue. 
It tracks a cursor, and allows in the implementation to add/insert text, read text, clear screen/scrollback and set stylistic attributes.

Build instructions:

`./gradlew build`
