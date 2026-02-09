package org.termbuf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTerminalLine {
    private TerminalLine line;
    @BeforeEach
    public void setup() {
        line = new TerminalLine(5);
    }

    @Test
    void testInitialCellsEmpty() {
        for (int i = 0; i < 5; i++) {
            assertEquals('\0', line.getCell(i).getCharacter());
        }
    }

    @Test
    void testFillLine() {
        line.fill('X');
        for (int i = 0; i < 5; i++) {
            assertEquals('X', line.getCell(i).getCharacter());
        }
    }

    @Test
    void testClearLine() {
        line.fill('A');
        line.clear();
        for (int i = 0; i < 5; i++) {
            assertEquals(' ', line.getCell(i).getCharacter());
        }
    }

    @Test
    void testToString() {
        line.fill('B');
        assertEquals("BBBBB", line.toString());
    }

    @Test
    void testInsertAtMiddle() {
        line.fill('0');
        TerminalCell cell = new TerminalCell();
        cell.setCharacter('X');

        line.insertAt(2, cell); // insert at index 2

        assertEquals('0', line.getCell(0).getCharacter());
        assertEquals('0', line.getCell(1).getCharacter());
        assertEquals('X', line.getCell(2).getCharacter());
        assertEquals('0', line.getCell(3).getCharacter());
        assertEquals('0', line.getCell(4).getCharacter());
    }

    @Test
    void testInsertAtStart() {
        line.fill('0');
        TerminalCell cell = new TerminalCell();
        cell.setCharacter('Y');

        line.insertAt(0, cell);

        assertEquals('Y', line.getCell(0).getCharacter());
        assertEquals('0', line.getCell(1).getCharacter());
    }

    @Test
    void testInsertAtEnd() {
        line.fill('0');
        TerminalCell cell = new TerminalCell();
        cell.setCharacter('Z');

        line.insertAt(4, cell);

        assertEquals('Z', line.getCell(4).getCharacter());
        assertEquals('0', line.getCell(3).getCharacter());
    }

}
