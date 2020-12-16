package a31.connect4.view;

import a31.connect4.controller.Game;
import a31.connect4.model.Player;
import a31.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame implements Observer {
    private static final ArrayList<MainWindow> vues = new ArrayList<>();
    protected Game game;
    protected Player player;

    public MainWindow(Game g) {
        //partie = new Game("Emile", "Jules", 4);
        this.game=g;
        //this.player=p;
        setVisible(true);
        setSize(400, 400);

        //Panel haut avec noms Joueur + couleur
        JPanel jpTop = new JPanel(new GridLayout(1, 4));
        jpTop.add(new JPanel()).setBackground(Color.YELLOW);
        jpTop.add(new JLabel("  "+game.getPlayers()[0].getName()));
        jpTop.add(new JLabel(game.getPlayers()[1].getName()));
        jpTop.add(new JPanel()).setBackground(Color.RED);
        jpTop.getComponent(0).setSize(20, 20);

        this.add(jpTop, BorderLayout.NORTH);
        jpTop.setSize(400, 20);
        jpTop.setOpaque(true);
        setLocationRelativeTo(null);

        //Grille principale
        GridBagLayout gbl=new GridBagLayout();
        //setLayout(gbl);
        //Permet de choisir les coordonnées des items que l'on veut placer dans la GBL
        //https://www.tutorialspoint.com/what-are-the-differences-between-gridlayout-and-gridbaglayout-in-java
        GridBagConstraints gbc=new GridBagConstraints();
        for (int i=0; i<game.getGrid().getHeight();i++){
            for(int j=0; i<game.getGrid().getWidth();j++){
                JPanel jp=new JPanel();
                jp.setBackground(Color.BLUE);
                gbc.gridx=j;
                gbc.gridy=i;
                gbl.addLayoutComponent(jp,gbc);
            }
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
