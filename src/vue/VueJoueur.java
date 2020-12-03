package vue;

import connect4.Joueur;
import connect4.Partie;
import observateur.Observateur;

import javax.swing.*;
import java.util.ArrayList;

public class VueJoueur extends JFrame implements Observateur {
    protected Partie partie;
    protected Joueur joueur;
    private static ArrayList<VueJoueur> joueurs=new ArrayList<>();

    public VueJoueur(Partie partie, Joueur joueur){

    }
    @Override
    public void mettreAjour() {

    }

}
