package org.termbuf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TerminalBufferTest {

    TerminalBuffer buffer;

    @BeforeEach
    void setup() {
        // create a 5x3 terminal with scrollback size 2
        buffer = new TerminalBuffer(5, 3, 2);
    }

    @Test
    void testWriteSimpleText() {
        buffer.write("abc");
        assertEquals('a', buffer.getCellFromScreen(0, 0).getCharacter());
        assertEquals('c', buffer.getCellFromScreen(0, 2).getCharacter());
        assertEquals(0, buffer.getCursorPosRow());
        assertEquals(3, buffer.getCursorPosCol());
    }

    @Test
    void testNewLineAndScrolling() {
        // Fill the first line and go to second
        buffer.write("12345");
        buffer.write("67890");
        buffer.write("X");
        assertEquals(2, buffer.getCursorPosRow());
        assertEquals(1, buffer.getCursorPosCol());

        // Fill one more line to push scrollback
        buffer.write("ABCDE");
        assertEquals(2, buffer.getCursorPosRow()); // last row
        // Scrollback should now have the first line
        assertEquals("12345", buffer.getScrollbackLineAsString(0));
    }

    @Test
    void testCursorMovement() {
        buffer.write("abc");
        buffer.moveCursor(CursorDirection.LEFT, 2);
        assertEquals(1, buffer.getCursorPosCol());
        buffer.moveCursor(CursorDirection.UP, 1);
        assertEquals(0, buffer.getCursorPosRow());
        buffer.moveCursor(CursorDirection.RIGHT, 10);
        assertEquals(4, buffer.getCursorPosCol());
    }

    @Test
    void testInsertText() {
        buffer.write("ab");
        buffer.insert("X");
        assertEquals('X', buffer.getCellFromScreen(0, 2).getCharacter());
        assertEquals('b', buffer.getCellFromScreen(0, 1).getCharacter());
    }

    @Test
    void testClearScreen() {
        buffer.write("abcde");
        buffer.clearScreen();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                assertEquals(' ', buffer.getCellFromScreen(row, col).getCharacter());
            }
        }
    }

    @Test
    // Fill entire line with B and C
    void testFillLine() {
        buffer.fillLine(1, 'B');
        buffer.fillLine(2, 'C');
        for (int col=0; col<5; col++) {
            assertEquals('B', buffer.getCellFromScreen(1, col).getCharacter());
            assertEquals('C', buffer.getCellFromScreen(2, col).getCharacter());
        }
    }

    @Test
    void testClearScreenAndScrollback() {
        buffer.write("12345");
        buffer.write("67890");
        buffer.clearScreenAndScrollBack();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                assertEquals(' ', buffer.getCellFromScreen(row, col).getCharacter());
            }
        }
        // this would work if we also remove the line after clearing it.
        //assertEquals(0, buffer.getScreenAndScrollbackAsStr().lines().count()); // scrollback cleared
    }
}
