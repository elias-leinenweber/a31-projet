import connect4.*;
import vue.VueJoueur;

public class Connect4 {

    // Création du contrôleur
    Partie partie = new Partie("Emile","Julien",4);

    // Création des quatre interfaces graphiques
		for( Joueur j: partie.getJoueurs() ){
        VueJoueur.getvues().add(new VueJoueur(ctrl, j));
    }
}
