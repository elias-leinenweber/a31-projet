package a31.connect4.model;

/**
 * Modélise un joueur.
 */
public class Player {
    private final String name;
    private Checker color;
    private int wins;

    public Player(String name, Checker color) {
        if (color == Checker.NONE)
            throw new IllegalArgumentException("Couleur invalide : " + color);
        this.name = name;
        this.color = color;
        wins = 0;
    }

    public String getName() {
        return name;
    }

    public Checker getColor() {
        return color;
    }

    public void switchColor() {
        color = color == Checker.YELLOW ? Checker.RED : Checker.YELLOW;
    }

    public int getWins() {
        return wins;
    }

    public void winGame() {
        ++wins;
    }
}
