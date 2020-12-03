package connect4;

/**
 * Modélise la grille de jeu
 */
public class Grille {
    private final Couleur[][] grille;
    private final int largeur;
    private final int hauteur;

    public Grille(int l, int h) {
        this.largeur = l;
        this.hauteur = h;
        grille = new Couleur[largeur][hauteur];
        for (Couleur[] ligne : grille) {
            for (Couleur slot : ligne) {
                slot = Couleur.GRIS;
            }
        }
    }

    public boolean jouer(Couleur c, int colonne) {
        // On parcourt la colonne en partant du bas...
        int ligne = 0;
        boolean pleine = false;

        // ...jusqu'à trouver une case vide :
        while (!pleine && grille[ligne][colonne] != Couleur.GRIS)
            ++ligne;

        // on remplit la case vide trouvée :
        grille[ligne][colonne] = c;
        return false;
    }

    public int getLargeur() {
        return largeur;
    }

    public boolean aUnGagnant() {
        return false;
    }

    public Joueur getGagnant() {
        return null;
    }

    public void vider() {

    }
}
