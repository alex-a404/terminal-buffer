package org.termbuf;


public class TerminalLine {
    private final TerminalCell[] cells;

    public TerminalLine(int width) {
        cells = new TerminalCell[width];
        for (int i = 0; i < width; i++) {
            cells[i] = new TerminalCell();
        }
    }

    public TerminalCell getCell(int pos) {
        return cells[pos];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TerminalCell cell : cells) {
            sb.append(cell.getCharacter());
        }
        return sb.toString();
    }

    public void clear() {
        fill(' ');
    }

    public void fill(char c){
        for (TerminalCell cell : cells) {
            cell.setCharacter(c);
        }
    }

    public void insertAt(int col, TerminalCell cell) {
        for (int i = cells.length - 1; i > col; i--) {
            cells[i] = cells[i - 1];
        }
        cells[col] = cell;
    }

}
