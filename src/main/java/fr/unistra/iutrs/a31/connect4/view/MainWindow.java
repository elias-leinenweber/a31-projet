package fr.unistra.iutrs.a31.connect4.view;

import fr.unistra.iutrs.a31.connect4.controller.Game;
import fr.unistra.iutrs.a31.connect4.model.Checker;
import fr.unistra.iutrs.a31.connect4.model.Player;
import fr.unistra.iutrs.a31.connect4.model.Rules;
import fr.unistra.iutrs.a31.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.stream.IntStream;

public class MainWindow extends Connect4Window implements Observer {
    private Game game;
    private final JButton[] buttons;
    private final JLabel[][] grid;
    private final JLabel lblStatus;

    public MainWindow() {
        super("Puissance 4");

        initMenuBar();

        buttons = new JButton[Rules.getColumns()];
        for (int i = 0; i < Rules.getColumns(); ++i) {
            buttons[i] = new JButton(String.valueOf(i + 1));
            buttons[i].setEnabled(false);
            buttons[i].setFocusable(false);
        }

        /* Le panel principal */
        grid = new JLabel[Rules.getRows()][Rules.getColumns()];
        initMainPanel();

        lblStatus = new JLabel("Tour n°");

        JToolBar barStatus = new JToolBar();
        barStatus.setFloatable(false);
        barStatus.add(lblStatus);
        add(barStatus, BorderLayout.PAGE_END);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initMenuBar() {
        JMenu gameMenu = new JMenu("Partie");
        gameMenu.setMnemonic('P');

        JMenuItem newGameItem = new JMenuItem("Nouvelle partie");
        newGameItem.setMnemonic('N');

        JMenuItem settingsItem = new JMenuItem("Options");
        settingsItem.setMnemonic('O');

        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.setMnemonic('Q');

        JMenu helpMenu = new JMenu("?");
        helpMenu.setMnemonic('?');

        JMenuItem tutorialItem = new JMenuItem("Tutoriel");
        tutorialItem.setMnemonic('T');

        /* Écouteurs d'événements. */
        newGameItem.addActionListener(e -> newGame());
        settingsItem.addActionListener(e -> new SettingsWindow(this));
        exitItem.addActionListener(e -> System.exit(0));
        tutorialItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Appuyez sur les boutons ou bien sur les touches 1-" +
            Rules.getColumns() + " sur votre clavier pour insérer un nouveau jeton.\n" +
            "Pour gagner vous devez aligner " + Rules.getInARow() + " jetons d'affilée, " +
            "horizontalement, verticalement ou diagonalement.", "Comment jouer ?",
            JOptionPane.INFORMATION_MESSAGE));

        gameMenu.add(newGameItem);
        gameMenu.add(settingsItem);
        gameMenu.add(exitItem);
        helpMenu.add(tutorialItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void initMainPanel() {
        /* Le panel pour les boutons */
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, Rules.getColumns(), 10, 0));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
        for (JButton button : buttons)
            pnlButtons.add(button);

        /* Le label pour l'image de la grille */
        ImageIcon boardImageIcon = ResourceLoader.getBoardImageIcon();
        JLabel lblBoard = new JLabel(boardImageIcon);
        lblBoard.setBounds(20, 20, boardImageIcon.getIconWidth(), boardImageIcon.getIconHeight());

        /* Le panel pour la grille */
        JLayeredPane lpnBoard = new JLayeredPane();
        lpnBoard.setPreferredSize(new Dimension(Rules.getColumns() * 76 + 38, Rules.getRows() * 76 + 38));
        lpnBoard.add(lblBoard, 0, 1);

        /* Crée les labels correspondant à toutes les cases */
        for (int i = 0; i < Rules.getRows(); ++i)
            for (int j = 0; j < Rules.getColumns(); ++j) {
                grid[i][j] = new JLabel();
                grid[i][j].setBounds(75 * j + 27, 75 * (Rules.getRows() - i - 1) + 27,
                                     ResourceLoader.RED.getIconWidth(),
                                     ResourceLoader.RED.getIconHeight());
                lpnBoard.add(grid[i][j], 0, 0);
            }

        /* Le panel principal */
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlMain.add(pnlButtons, BorderLayout.NORTH);
        pnlMain.add(lpnBoard, BorderLayout.CENTER);

        getContentPane().add(pnlMain, BorderLayout.CENTER);
    }

    private void newGame() {
        String player1, player2;
        Integer winsNeeded;

        do {
            player1 = (String)promptUserInput("Veuillez entrer le nom du joueur 1 :",
                                              "Nom du joueur 1", null);
            if (player1 == null)
                return;
        } while (player1.length() < 1);
        do {
            player2 = (String)promptUserInput("Veuillez entrer le nom du joueur 2 :",
                                              "Nom du joueur 2", null);
            if (player2 == null)
                return;
        } while (player2.length() < 1);
        winsNeeded = (Integer)promptUserInput(
                    "Veuillez entrer le nombre de manches nécessaires pour gagner :",
                    "Nombre de manches", IntStream.range(1, 11).boxed().toArray(Integer[]::new));
        if (winsNeeded == null)
            return;

        newGame(player1, player2, winsNeeded);
    }

    private void newGame(String player1, String player2, int winsNeeded) {
        game = new Game(player1, player2, winsNeeded);
        game.register(this);
        update();
        setAllButtonsEnabled(true);
    }

    private Object promptUserInput(String message, String title, Integer[] selectionValues) {
        return JOptionPane.showInputDialog(this, message, title, JOptionPane.QUESTION_MESSAGE, null,
                                           selectionValues, null);
    }

    private void setAllButtonsEnabled(boolean b) {
        if (b) {
            for (int i = 0; i < buttons.length; ++i) {
                JButton button = buttons[i];

                button.setEnabled(true);
                if (button.getActionListeners().length == 0) {
                    int finalI = i;
                    button.addActionListener(e -> {
                        makeMove(finalI);
                        requestFocusInWindow();
                    });
                }
            }

            /* Écouteur de touches (raccourcis clavier pour les boutons) */
            if (getKeyListeners().length == 0)
                addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}

                    @Override
                    public void keyPressed(KeyEvent e) {
                        char keyChar = e.getKeyChar();
                        if (Character.isDigit(keyChar))
                            makeMove(Character.digit(keyChar, 10) - 1);
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {}
                });
        } else {
            for (JButton button : buttons) {
                button.setEnabled(false);
                for (ActionListener actionListener : button.getActionListeners())
                    button.removeActionListener(actionListener);
            }

            removeKeyListener(getKeyListeners()[0]);
        }
    }

    private void makeMove(int col) {
        game.play(game.getCurrentPlayer(), col);
    }

    @Override
    public void update() {
        Player player1 = game.getPlayers()[0];
        Player player2 = game.getPlayers()[1];
        lblStatus.setText("Tour n° " + game.getTurn() +
                " (" + game.getCurrentPlayer().getName() + ")" +
                " | " + player1.getName() + " " + player1.getWins() +
                " - " + player2.getWins() + " " + player2.getName());

        Checker[][] checkers = game.getGrid().getCheckers();
        for (int i = 0; i < Rules.getRows(); ++i)
            for (int j = 0; j < Rules.getColumns(); ++j)
                switch (checkers[i][j]) {
                    case RED:
                        grid[i][j].setIcon(ResourceLoader.RED);
                        break;
                    case YELLOW:
                        grid[i][j].setIcon(ResourceLoader.YELLOW);
                        break;
                    case NONE:
                        grid[i][j].setIcon(null);
                        break;
                }

        if (game.isOver()) {
            setAllButtonsEnabled(false);

            if (JOptionPane.showConfirmDialog(
                    null, game.getWinner().getName() + " a gagné ! Faire une revanche ?",
                    "Partie terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                newGame(game.getPlayers()[0].getName(),
                        game.getPlayers()[1].getName(),
                        game.getWinsNeeded());
        }
    }
}
