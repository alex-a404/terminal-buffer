package org.termbuf;

public class TerminalCell {
    private char character;
    private TerminalAttributes attrs;

    public void setCharacter(char character) {this.character = character;}
    public char getCharacter() {return character;}

    public void setAttributes(TerminalAttributes attrs) {this.attrs = attrs;}
    public TerminalAttributes getAttributes() {return attrs;}

}
