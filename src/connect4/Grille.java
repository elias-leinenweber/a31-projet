package connect4;

/**
 * Modélise la grille de jeu
 */
public class Grille {
    private int longueur;
    private int hauteur;

    public Grille(int l, int h){
        this.longueur=l;
        this.hauteur=h;
        Couleur[][] grille=new Couleur [longueur][hauteur];
        for (Couleur[] ligne : grille){
            for (Couleur slot : ligne){
                 slot= Couleur.GRIS;
            }
        }
    }
}
