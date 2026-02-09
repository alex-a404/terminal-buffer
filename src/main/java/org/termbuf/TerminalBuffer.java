package org.termbuf;

import java.util.*;

/**
 * The basic logical backend/structure of any terminal.
 */
public class TerminalBuffer {
    private int cursorRow;
    private int cursorCol;
    private final int scrollbackSize;
    private final int width;
    private final int height;
    private TerminalAttributes attrs;

    private final ArrayList<TerminalLine> screen = new ArrayList<>();
    private final ArrayDeque<TerminalLine> scrollback = new ArrayDeque<>();

    public TerminalBuffer(int width, int height, int scrollbackSize) {
        this.width = width;
        this.height = height;
        this.scrollbackSize = scrollbackSize;
        this.cursorRow = 0;
        this.cursorCol = 0;
        attrs = new TerminalAttributes(0,0,false,false,false);

        // initialize onscreen lines
        for (int i=0; i<height; i++) {
            screen.add(new TerminalLine(width));
        }
    }

    /**
     * Sets global default colors and style settings (replaces all at once)
     * @param attrs TerminalAttributes type with desired colors/styles.
     */
    public void setAttributes(TerminalAttributes attrs) {
        this.attrs = attrs;
    }

    /**
     * Write text to the position where the cursor is set.
     * @param text Text to write
     */
    public void write(String text){

        char[] chars = text.toCharArray();
        for (char cc : chars) {
            if (cc == '\n') {
                newLine();
                continue;
            }
            TerminalCell cell = getCellFromScreen(cursorRow, cursorCol);
            cell.setCharacter(cc);
            cell.setAttributes(attrs);
            cursorCol++;
            if (cursorCol>=width) {
                newLine();
            }
        }
    }

    /**
     *
     * @param text text to insert
     */
    public void insert(String text) {
        for (char c : text.toCharArray()) {
            if (c == '\n') {
                newLine();
                continue;
            }
            TerminalCell cell = new  TerminalCell();
            cell.setCharacter(c);
            cell.setAttributes(attrs);
            screen.get(cursorRow).insertAt(cursorCol, cell);

            cursorCol++;
            if (cursorCol >= width) newLine();
        }
    }

    /**
     * Adds a new empty line at the bottom of the screen
     */
    public void insertEmptyLineAtBottom() {
        if (screen.size() == height) {
            scrollback.addLast(screen.remove(0));;
            if (scrollback.size() > scrollbackSize) {
                scrollback.removeFirst();
            }
        }
        screen.add(new TerminalLine(width));
    }


    /**
     * Skip to the next line
     */
    public void newLine(){
        // reset cursor positions to new line
        cursorRow++;
        cursorCol = 0;
        if (cursorRow >= height) {
            scrollback.addLast(screen.remove(0));
            if (scrollback.size() > scrollbackSize) {
                scrollback.removeFirst();
            }
            screen.add(new TerminalLine(width));
            cursorRow = height - 1;
        }
    }


    /**
     * Moves the cursor in a given direction by a given amount of cells
     * @param dir UP, DOWN, LEFT or RIGHT
     * @param n the number of terminal cells to move cursor by
     */
    public void moveCursor(CursorDirection dir, int n){
        switch (dir) {
            case LEFT -> cursorCol = Math.max(0,cursorCol-n);
            case RIGHT -> cursorCol = Math.min(width-1,cursorCol+n);
            case UP -> cursorRow = Math.max(0, cursorRow-n);
            case DOWN -> cursorRow=Math.min(height-1, cursorRow+n);
        }
    }

    // Getters and setters for cursor
    public int getCursorPosRow() {return cursorRow;}
    public int getCursorPosCol() {return cursorCol;}
    public void setCursorPos(int row, int col) {
        cursorRow = Math.max(0, Math.min(height - 1, row));
        cursorCol = Math.max(0, Math.min(width - 1, col));
    }

    // Getters for terminal content

    /**
     * Get cell: Character and attributes from screen
     * @param row Row to get character/attributes in
     * @param col Column to get character/attributes in
     * @return TerminalCell object with character and attributes
     */
    public TerminalCell getCellFromScreen(int row, int col) {return screen.get(row).getCell(col);}

    /**
     * Get cell: Character and attributes from scrollback
     * @param row Row to get character/attributes in
     * @param col Column to get character/attributes in
     * @return TerminalCell object with character and attributes
     */
    public TerminalCell getCellFromScrollback(int row, int col) {
        TerminalLine line = new ArrayList<>(scrollback).get(row);
        return line.getCell(col);
    }

    /**
     * Get the row on the screen as str.
     * @param row Number of row to get as string
     * @return String of row
     */
    public String getLineAsStr(int row){return screen.get(row).toString();}

    /**
     * Get row in scrollback as string
     * @param row number of row to get as screen in scrollback
     * @return String of row in scrollback
     */
    public String getScrollbackLineAsString(int row) {
        return new ArrayList<>(scrollback).get(row).toString();
    }

    /**
     * Get the entire screen as a string
     */
    public String getScreenAsStr(){
        StringBuilder sb = new StringBuilder();
        for (TerminalLine line : screen) {
            sb.append(line.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Get the screen and scrollback as string
     */
    public String getScreenAndScrollbackAsStr(){
        StringBuilder sb = new StringBuilder();
        for (TerminalLine line : scrollback) {
            sb.append(line.toString()).append("\n");
        }
        sb.append(getScreenAsStr());
        return sb.toString();
    }

    /**
     * Clear the entire screen
     */
    public void clearScreen(){
        for (TerminalLine line : screen) {
            line.clear();
            //screen.remove(line);
        }
    }

    /**
     * Clear the entire screen + scrollback
     */
    public void clearScreenAndScrollBack(){
        clearScreen();
        scrollback.clear();
    }

    /**
     * Fill a line on the screen with one character.
     * @param line line to fill(on screen)
     * @param c character to fill line with
     */
    public void fillLine(int line, char c){
        TerminalLine l = screen.get(line);
        l.fill(c);
    }
}

