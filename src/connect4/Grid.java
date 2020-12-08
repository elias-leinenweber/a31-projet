package connect4;

import java.util.Arrays;

/**
 * Modélise la grille de jeu.
 */
public class Grid {
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

    public boolean drop(Checker checker, int column) {
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
        return !isColumnFull;
    }

    public boolean hasWinningColor() {
        return hasWon(Checker.YELLOW) || hasWon(Checker.RED);
    }

    private boolean hasWon(Checker playerColor) {
        boolean res = false;

        for (int row = 0; row < height; ++row)
            for (int column = 0; column < width; ++column)
                if (grid[row][column] == playerColor && (count(row, column, -1, +1) >= Rules.IN_A_ROW || count(row, column, 0, +1) >= Rules.IN_A_ROW || count(row, column, +1, +1) >= Rules.IN_A_ROW || count(row, column, +1, 0) >= Rules.IN_A_ROW)) {
                    winningColor = playerColor;
                    res = true;
                    break;
                }

        return res;
    }

    private int count(int ligneDepart, int colonneDepart, int dirLigne, int dirColonne) {
        int compteur = 0;
        int ligne = ligneDepart;
        int colonne = colonneDepart;

        while (grid[ligne][colonne] == grid[ligneDepart][colonneDepart]) {
            ++compteur;
            ligne += dirLigne;
            colonne += dirColonne;
        }

        return compteur;
    }

    public Checker getWinningColor() {
        return winningColor;
    }

    public int getWidth() {
        return width;
    }
}
