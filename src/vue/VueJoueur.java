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

    public VueJoueur(){
        Partie partie=new Partie("Emile","Jules",4 );
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
    }

    public static ArrayList<VueJoueur> getVues(){return vues;}

    @Override
    public void mettreAjour() {

    }

}
