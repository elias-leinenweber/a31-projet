package a31.connect4.model;

import java.util.Arrays;

/**
 * Modélise la grille de jeu.
 */
public class Grid {
    private static final int UP = +1;
    private static final int DOWN = -1;
    private static final int LEFT = -1;
    private static final int RIGHT = +1;

    private final Checker[][] grid;
    private final int height;
    private final int width;
    private Checker winningColor;

    public Grid(int rows, int columns) {
        if (rows <= 0 || columns <= 0)
            throw new IllegalArgumentException("Dimensions invalides : " + rows + " par " + columns);
        height = rows;
        width = columns;

        grid = new Checker[rows][columns];
        clear();

        winningColor = Checker.NONE;
    }

    public void clear() {
        for (Checker[] row : grid)
            Arrays.fill(row, Checker.NONE);
    }

    public int drop(Checker checker, int column) {
        if (checker == Checker.NONE || column < 0 || column >= width)
            throw new IllegalArgumentException();
        /* On parcourt la colonne en partant du bas... */
        int row = 0;
        boolean isColumnFull = false;

        /* ...jusqu'à trouver une case vide. */
        while (!isColumnFull && grid[row][column] != Checker.NONE)
            if (row == height)
                isColumnFull = true;
            else
                ++row;

        /* On remplit la case vide trouvée. */
        if (!isColumnFull)
            grid[row][column] = checker;
        return isColumnFull ? -1 : row;
    }

    public boolean hasWinningColor() {
        return hasWon(Checker.YELLOW) || hasWon(Checker.RED);
    }

    private boolean hasWon(Checker playerColor) {
        boolean res = false;

        for (int row = 0; row < height; ++row)
            for (int column = 0; column < width; ++column)
                if (grid[row][column] == playerColor &&
                    (count(row, column, -1, +1) >= Rules.IN_A_ROW ||
                     count(row, column,  0, +1) >= Rules.IN_A_ROW ||
                     count(row, column, +1, +1) >= Rules.IN_A_ROW ||
                     count(row, column, +1,  0) >= Rules.IN_A_ROW)) {
                    winningColor = playerColor;
                    res = true;
                    break;
                }

        return res;
    }

    private int count(int startRow, int startColumn, int rowDirection, int columnDirection) {
        int counter = 0;
        int row = startRow;
        int column = startColumn;

        while ((row >= 0 && row < height && column >= 0 && column < width) &&
                grid[row][column] == grid[startRow][startColumn]) {
            ++counter;
            row += rowDirection;
            column += columnDirection;
        }
        return counter;
    }

    public Checker getWinningColor() {
        return winningColor;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){ return height;}

    public boolean isOverflow() {
        for (int i = 0; i < grid[0].length; ++i)
            if (grid[0][i] == Checker.NONE)
                return false;
        return true;
    }
}
