package connect4;

public class Joueur {
    private final String nom;
    private int points;

    public Joueur(String nom) {
        this.nom = nom;
        points = 0;
    }

    public String getNom() {
        return nom;
    }

    public int getPoints() {
        return points;
    }

    public void remporterManche() {
        ++points;
    }
}
