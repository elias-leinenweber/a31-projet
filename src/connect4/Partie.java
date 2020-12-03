package connect4;

public class Partie extends Sujet {

    private final Joueur[] joueurs;
    private final Grille grille;
    private final int nbManches;
    private int indiceJoueurCourant;
    private int manche;
    private boolean terminee;

    public Partie(String nomJoueur1, String nomJoueur2, int nbManches) {
        joueurs = new Joueur[2];
        joueurs[0] = new Joueur(nomJoueur1);
        joueurs[1] = new Joueur(nomJoueur2);
        indiceJoueurCourant = 0;

        this.nbManches = nbManches;
        manche = 0;
        terminee = false;
    }

    public void jouer(Joueur j, int rangee) {
        // Si le coup est valide
        if (j == joueurs[indiceJoueurCourant] && verifierRangee(rangee)) {
            grille.jouer(j, rangee);
            verifierGagnant();
            indiceJoueurCourant = (indiceJoueurCourant + 1) % 2;
        }
    }

    private boolean verifierRangee(int rangee) {
        return rangee >= 0 && rangee < grille.getLargeur();
    }

    private void verifierGagnant() {
        if (grille.aUnGagnant()) {
            grille.getGagnant().remporterManche();
            nouvelleManche();
        }
    }

    private void nouvelleManche() {
        // si partie terminée
        if (++manche > nbManches)
            fin();
        else
            grille.vider();
    }

    private void fin() {
        terminee = true;
    }

    public boolean estTerminee() {
        return terminee;
    }

    public Joueur getGagnant() {
        Joueur gagnant = null; // match nul
        int diff = joueurs[0].getPoints() - joueurs[1].getPoints();
        if (diff > 0)
            gagnant = joueurs[0];
        else /* diff < 0 */
            gagnant = joueurs[1];
        return gagnant;
    }
}
