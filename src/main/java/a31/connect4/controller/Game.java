package a31.connect4.controller;

import a31.connect4.model.Checker;
import a31.connect4.model.Grid;
import a31.connect4.model.Player;
import a31.connect4.model.Rules;
import a31.observer.Subject;

import static java.util.Objects.requireNonNull;

/**
 * La classe "contrôleur" du jeu.
 */
public class Game extends Subject {

    private final Player[] players;
    private final Grid grid;
    private final int winsNeeded;
    private int currentPlayerIndex;
    private int wins;
    private boolean isOver;
    private int turn;

    /**
     * Crée une nouvelle partie.
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

        grid = new Grid(Rules.ROWS, Rules.COLUMNS);

        this.winsNeeded = winsNeeded;
        wins = 0;
        isOver = false;
        turn = 1;

        notifyObservers();
    }

    /**
     * Tente de retourner un jeton dans une colonne
     * retourne le numéro de la colonne
     *  si la colonne est pleine, retourne -1
     *
     * @param player le joueur
     * @param column le numéro de la colonne
     * @return row la ligne où placer le jeton
     */
    public int play(Player player, int column) {
        int row = -1;
        if (player == players[currentPlayerIndex] && checkColumn(column)) {
            if ((row = grid.drop(player.getColor(), column)) != -1) {
                verifierGagnant();
                currentPlayerIndex = (currentPlayerIndex + 1) % 2;
                ++turn;
                notifyObservers();
            }
        }
        return row;
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
        if (players[0].getWins() >= winsNeeded|| players[1].getWins()>=winsNeeded)
            isOver = true;
        else {
            grid.clear();
            turn = 1;
        }
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

    public int getTurn() {
        return turn;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public int getWinsNeeded() {
        return winsNeeded;
    }
}
