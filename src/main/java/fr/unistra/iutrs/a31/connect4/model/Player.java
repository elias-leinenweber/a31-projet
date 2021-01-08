package fr.unistra.iutrs.a31.connect4.model;

/**
 * Modélise un joueur dans le jeu de Puissance 4.
 */
public class Player {
    private final String name;
    private Checker color;
    private int wins;

    /**
     * Crée un joueur.
     *
     * @param name le nom du joueur
     * @param color la couleur de jeton du joueur
     * @throws IllegalArgumentException si le jeton n'a pas de couleur
     */
    public Player(String name, Checker color) {
        if (color == Checker.NONE)
            throw new IllegalArgumentException("Couleur invalide : " + color);
        this.name = name;
        this.color = color;
        wins = 0;
    }

    /**
     * Intervertit la couleur de ce joueur et d'un autre joueur
     * (son adversaire)
     *
     * @param player l'autre joueur
     */
    public void switchColor(Player player) {
        color = color == Checker.YELLOW ? Checker.RED : Checker.YELLOW;
        player.color = color == Checker.RED ? Checker.YELLOW : Checker.RED;
    }

    /**
     * Remporte une manche.
     */
    public void winGame() {
        ++wins;
    }

    public String getName() {
        return name;
    }

    public Checker getColor() {
        return color;
    }

    public int getWins() {
        return wins;
    }
}
