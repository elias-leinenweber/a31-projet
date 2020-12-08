package vue;

import connect4.Game;
import connect4.Player;
import observer.Observer;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class VueJoueur extends JFrame implements Observer {
    private static final ArrayList<VueJoueur> vues = new ArrayList<>();
    protected Game partie;
    protected Player joueur;

    public VueJoueur() {
        partie = new Game("Emile", "Jules", 4);
        setVisible(true);
        setSize(400, 400);

        //Panel haut avec noms Joueur + couleur
        JPanel jpTop = new JPanel(new GridLayout(1, 4));
        jpTop.add(new JPanel()).setBackground(Color.YELLOW);
        jpTop.add(new JLabel(partie.getPlayers()[0].toString()));
        jpTop.add(new JLabel(partie.getPlayers()[1].toString()));
        jpTop.add(new JPanel()).setBackground(Color.RED);
        jpTop.getComponent(0).setSize(20, 20);

        this.add(jpTop, BorderLayout.NORTH);
        jpTop.setSize(400, 20);
        jpTop.setOpaque(true);
        setLocationRelativeTo(null);

        /*ImageIcon board = new ImageIcon( getClass().getClassLoader().getResource("Board.png") ); // dans le dossier bin/
        add(new JLabel(board));
        board.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
        //this.add(new GridLayout(partie.getGrid().getLargeur(), partie.getGrid.getHauteur()));
        int i =0;
        //while (i<*/
    }

    public static ArrayList<VueJoueur> getVues() {
        return vues;
    }

    @Override
    public void update() {
    }
}
