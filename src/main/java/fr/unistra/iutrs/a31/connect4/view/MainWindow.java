package fr.unistra.iutrs.a31.connect4.view;

import fr.unistra.iutrs.a31.connect4.controller.Game;
import fr.unistra.iutrs.a31.connect4.model.Player;
import fr.unistra.iutrs.a31.observer.Observer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MainWindow extends JFrame implements Observer {
    private static final ArrayList<MainWindow> vues = new ArrayList<>();
    protected Game partie;
    protected Player joueur;

    public MainWindow() {
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

    public static List<MainWindow> getVues() {
        return vues;
    }

    @Override
    public void update() {
    }
}
