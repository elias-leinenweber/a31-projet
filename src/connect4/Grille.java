package connect4;

/**
 * Modélise la grille de jeu
 */
public class Grille {
    private final Couleur[][] grille;
    private final int largeur;
    private final int hauteur;
    private Couleur gagnant;

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
        while (!pleine && grille[ligne][colonne] != Couleur.GRIS) {
            if (ligne == hauteur)
                pleine = true;
            else
                ++ligne;
        }

        // on remplit la case vide trouvée :
        if (!pleine)
            grille[ligne][colonne] = c;
        return !pleine;
    }

    public int getLargeur() {
        return largeur;
    }

    public boolean aUnGagnant() {
        return aGagne(Couleur.JAUNE) || aGagne(Couleur.ROUGE);
    }

    public boolean aGagne(Couleur couleurJoueur) {
        Couleur couleurCase;

        for (int ligne = 0; ligne < hauteur; ++ligne)
            for (int colonne = 0; colonne < largeur; ++colonne) {
                couleurCase = grille[ligne][colonne];

                if (couleurCase == couleurJoueur) {
                    if (compte(ligne, colonne, -1, +1) >= Regles.IN_A_ROW ||
                            compte(ligne, colonne, 0, +1) >= Regles.IN_A_ROW ||
                            compte(ligne, colonne, +1, +1) >= Regles.IN_A_ROW ||
                            compte(ligne, colonne, +1, 0) >= Regles.IN_A_ROW) {
                        gagnant = couleurJoueur;
                        return true;
                    }
                }
            }
        return false;
    }

    private int compte(int ligneDepart, int colonneDepart, int dirLigne, int dirColonne) {
        int compteur = 0;
        int ligne = ligneDepart;
        int colonne = colonneDepart;

        while (grille[ligne][colonne] == grille[ligneDepart][colonneDepart]) {
            ++compteur;
            ligne += dirLigne;
            colonne += dirColonne;
        }

        return compteur;
    }

    public Couleur getGagnant() {
        return gagnant;
    }

    public void vider() {

    }
}
