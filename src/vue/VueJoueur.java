package vue;

import connect4.Joueur;
import connect4.Partie;
import observateur.Observateur;

import javax.swing.*;
import java.util.ArrayList;

public class VueJoueur extends JFrame implements Observateur {
    protected Partie partie;
    protected Joueur joueur;
    private static ArrayList<VueJoueur> vues=new ArrayList<>();

    public VueJoueur(Partie partie, Joueur joueur){
        super("Puissance 4");
        this.joueur=joueur;
        this.partie=partie;

        partie.ajouterObservateur(this);

        setVisible(true);
    }

    public ArrayList<VueJoueur> getVues(){return vues;}

    @Override
    public void mettreAjour() {

    }

}
