package connect4;

public class Joueur {
    private final String nom;
    private int points;
    private Couleur couleur;

    public Joueur(String nom, Couleur couleur) {
        this.nom = nom;
        points = 0;
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public int getPoints() {
        return points;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void remporterManche() {
        ++points;
    }
}
