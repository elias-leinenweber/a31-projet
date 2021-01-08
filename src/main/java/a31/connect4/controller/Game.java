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
    private int turn;
    private boolean isOver;

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

        grid = new Grid(Rules.getRows(), Rules.getColumns());
        this.winsNeeded = winsNeeded;
        currentPlayerIndex = 0;
        turn = 1;
        isOver = false;

        notifyObservers();
    }

    /**
     * Tente de placer un jeton dans une colonne.
     *
     * @param player le joueur
     * @param column l'indice de la colonne
     */
    public void play(Player player, int column) {
        if (player == players[currentPlayerIndex] && checkColumn(column)) {
            if (grid.drop(player.getColor(), column) != -1) {
                checkWinner();
                currentPlayerIndex = (currentPlayerIndex + 1) % 2;
                ++turn;
                notifyObservers();
            }
        }
    }

    private boolean checkColumn(int column) {
        return column >= 0 && column < grid.getWidth();
    }

    private void checkWinner() {
        boolean gridHasWinningColor = grid.hasWinningColor();
        if (gridHasWinningColor)
            (players[0].getColor() == grid.getWinningColor() ? players[0] : players[1]).winGame();
        if (gridHasWinningColor || grid.isOverflow())
            newRound();
    }

    private void newRound() {
        if (players[0].getWins() >= winsNeeded || players[1].getWins() >= winsNeeded)
            isOver = true;
        else {
            players[0].switchColor(players[1]);
            grid.clear();
            turn = 0;
        }
    }

    /**
     * Renvoie le gagnant de la partie.
     *
     * @return le gagnant de la partie
     * @throws IllegalStateException si la partie n'est pas terminée
     */
    public Player getWinner() {
        if (!isOver)
            throw new IllegalStateException("Il n'y pas de gagnant tant que la partie n'est pas terminée !");

        /*
         * À partir de cet endroit, il est impossible que les deux joueurs aient
         * le même nombre de points.
         */
        return players[0].getWins() > players[1].getWins() ? players[0] : players[1];
    }

    public Player[] getPlayers() {
        return players;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getWinsNeeded() {
        return winsNeeded;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public int getTurn() {
        return turn;
    }

    public boolean isOver() {
        return isOver;
    }
}
