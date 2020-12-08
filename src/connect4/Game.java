package connect4;

import observer.Subject;

import static java.util.Objects.requireNonNull;

public class Game extends Subject {

    private final Player[] players;
    private final Grid grid;
    private final int winsNeeded;
    private int currentPlayerIndex;
    private int wins;
    private boolean isOver;

    /**
     *
     * @param player1    le nom du premier joueur
     * @param player2    le nom du deuxième joueur
     * @param winsNeeded le nombre de manches à jouer
     */
    public Game(String player1, String player2, int winsNeeded) {
        if (winsNeeded < 1)
            throw new IllegalArgumentException("Nombre de manches invalide : " + winsNeeded);
        players = new Player[2];
        players[0] = new Player(requireNonNull(player1), Checker.YELLOW);
        players[1] = new Player(requireNonNull(player2), Checker.RED);
        currentPlayerIndex = 0;

        grid = new Grid(Rules.COLUMNS, Rules.ROWS);

        this.winsNeeded = winsNeeded;
        wins = 0;
        isOver = false;
    }

    public void jouer(Player player, int column) {
        if (player == players[currentPlayerIndex] && checkColumn(column)) {
            if (grid.drop(player.getColor(), column)) {
                verifierGagnant();
                currentPlayerIndex = (currentPlayerIndex + 1) % 2;
                notifyObservers();
            }
        }
    }

    private boolean checkColumn(int column) {
        return column >= 0 && column < grid.getWidth();
    }

    private void verifierGagnant() {
        if (grid.hasWinningColor()) {
            if (players[0].getColor() == grid.getWinningColor())
                players[0].winGame();
            else
                players[1].winGame();
            nouvelleManche();
        }
    }

    private void nouvelleManche() {
        if (++wins > winsNeeded)
            isOver = true;
        else
            grid.clear();
    }

    public Player getWinner() {
        /* Match nul. */
        Player winner = null;
        int diff = players[0].getWins() - players[1].getWins();

        if (diff > 0)
            winner = players[0];
        else if (diff < 0)
            winner = players[1];
        return winner;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isOver() {
        return isOver;
    }
}
