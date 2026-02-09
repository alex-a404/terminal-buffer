package org.termbuf;

public record TerminalAttributes(
        int backgroundcol,
        int foregroundcol,
        boolean bold,
        boolean italic,
        boolean underline
){}
