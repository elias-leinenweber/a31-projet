package vue;

import connect4.Joueur;
import connect4.Partie;
import observateur.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VueJoueur extends JFrame implements Observateur {
    protected Partie partie;
    protected Joueur joueur;
    private static ArrayList<VueJoueur> vues=new ArrayList<>();

    public VueJoueur(){
        Partie partie=new Partie("Emile","Jules",4 );
        setVisible(true);
        setSize(400,400);

        //Panel haut avec noms Joueur + couleur
        JPanel jpTop = new JPanel(new GridLayout(1, 4));
        jpTop.add(new JPanel()).setBackground(Color.YELLOW);
        jpTop.add(new JLabel(partie.getJoueurs()[0].toString()));
        jpTop.add(new JLabel(partie.getJoueurs()[1].toString()));
        jpTop.add(new JPanel()).setBackground(Color.RED);
        jpTop.getComponent(0).setSize(20,20);

        this.add(jpTop, BorderLayout.NORTH);
        jpTop.setSize(400,20);
        jpTop.setOpaque(true);
        setLocationRelativeTo(null);

        /*ImageIcon board = new ImageIcon( getClass().getClassLoader().getResource("Board.png") ); // dans le dossier bin/
        add(new JLabel(board));
        board.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
        //this.add(new GridLayout(partie.getGrid().getLargeur(), partie.getGrid.getHauteur()));
        


    }
*/
    public static ArrayList<VueJoueur> getVues(){return vues;}

    @Override
    public void mettreAjour() {

    }

}
