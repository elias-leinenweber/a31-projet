package a31.connect4.model;

import java.util.Arrays;

/**
 * Modélise la grille de jeu.
 */
public class Grid {
    private static final int UP = +1;
    private static final int DOWN = -1;
    private static final int RIGHT = +1;

    private final Checker[][] grid;
    private final int height;
    private final int width;
    private Checker winningColor;

    /**
     * Crée une grille de Puissance 4.
     *
     * @param rows    le nombre de lignes
     * @param columns le nombre de colonnes
     * @throws IllegalArgumentException si les dimensions sont inférieures ou
     *                                  égales à 0
     */
    public Grid(int rows, int columns) {
        if (rows <= 0 || columns <= 0)
            throw new IllegalArgumentException("Dimensions invalides : " + rows + " par " + columns);

        grid = new Checker[rows][columns];
        height = rows;
        width = columns;
        winningColor = Checker.NONE;

        clear();

        /* Si on est en 5-in-a-Row, on ajoute les "sliders". */
        if (Rules.getColumns() == 9) {
            Checker color = Checker.YELLOW;
            for (int i = 0; i < Rules.getRows(); ++i) {
                grid[i][0] = color;
                color = color == Checker.YELLOW ? Checker.RED : Checker.YELLOW;
                grid[i][8] = color;
            }
        }
    }

    /**
     * Vide la grille.
     */
    public void clear() {
        for (Checker[] row : grid)
            Arrays.fill(row, Checker.NONE);
    }

    /**
     * Tente d'insérer un jeton dans une colonne.
     *
     * @param checker le jeton à insérer
     * @param column  l'indice de la colonne
     * @return la ligne dans laquelle le jeton a été inséré, si la colonne n'est
     *         pas remplie ; -1 sinon
     * @throws IllegalArgumentException si on tente d'insérer un jeton "vide" ou
     *                                  que l'indice de la colonne est invalide
     */
    public int drop(Checker checker, int column) {
        if (checker == Checker.NONE || column < 0 || column >= width)
            throw new IllegalArgumentException();

        /* On parcourt la colonne en partant du bas... */
        int row = 0;
        boolean isColumnFull = false;

        /* ...jusqu'à trouver une case vide. */
        while (!isColumnFull && grid[row][column] != Checker.NONE)
            if (row == height - 1)
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
                    (count(row, column, DOWN, RIGHT) >= Rules.getInARow() ||
                     count(row, column, 0, RIGHT) >= Rules.getInARow() ||
                     count(row, column, UP, RIGHT) >= Rules.getInARow() ||
                     count(row, column, UP, 0) >= Rules.getInARow())) {
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

    public boolean isOverflow() {
        for (int i = 0; i < grid[height - 1].length; ++i)
            if (grid[height - 1][i] == Checker.NONE)
                return false;
        return true;
    }

    public Checker[][] getCheckers() {
        return grid;
    }

    public int getWidth() {
        return width;
    }

    public Checker getWinningColor() {
        return winningColor;
    }
}
