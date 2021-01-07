package a31.connect4.view;

import a31.connect4.controller.Game;
import a31.connect4.model.Checker;
import a31.connect4.model.Player;
import a31.connect4.model.Rules;
import a31.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.stream.IntStream;

public class MainWindow extends JFrame implements Observer {

    private static final ImageIcon BOARD = new ImageIcon(MainWindow.class.getResource(String.format("/Board%dx%d.png", Rules.COLUMNS, Rules.ROWS)));
    private static final ImageIcon RED = new ImageIcon(MainWindow.class.getResource("/Red.png"));
    private static final ImageIcon YELLOW = new ImageIcon(MainWindow.class.getResource("/Yellow.png"));

    private static final int DEFAULT_WIDTH = Rules.COLUMNS * 76 + 38;
    private static final int DEFAULT_HEIGHT = 500;

    private Game game;
    private final JButton[] buttons;
    private final JLabel[][] grid;
    private final JLabel lblStatus;

    public MainWindow() {
        super("Puissance 4");
        setIconImage(new ImageIcon(getClass().getResource("/Icon.png")).getImage());
        initLookAndFeel();

        initMenuBar();

        buttons = new JButton[Rules.COLUMNS];
        for (int i = 0; i < Rules.COLUMNS; ++i) {
            buttons[i] = new JButton(String.valueOf(i + 1));
            buttons[i].setEnabled(false);
            buttons[i].setFocusable(false);
        }

        /* Le panel principal */
        grid = new JLabel[Rules.ROWS][Rules.COLUMNS];
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
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Partie");
        JMenuItem newGameItem = new JMenuItem("Nouvelle partie");
        JMenuItem settingsItem = new JMenuItem("Options");
        JMenuItem exitItem = new JMenuItem("Quitter");

        JMenu helpMenu = new JMenu("?");
        helpMenu.setMnemonic('?');
        JMenuItem tutorialItem = new JMenuItem("Tutoriel");

        /* Écouteurs d'événements. */
        newGameItem.addActionListener(e -> newGame());
        settingsItem.addActionListener(e -> new SettingsWindow());
        exitItem.addActionListener(e -> System.exit(0));

        tutorialItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Appuyez sur les boutons ou bien sur les touches 1-" +
            Rules.COLUMNS + " sur votre clavier pour insérer un nouveau jeton.\n" +
            "Pour gagner vous devez aligner " + Rules.IN_A_ROW + " jetons d'affilée, " +
            "horizontalement, verticalement ou diagonalement.", "Comment jouer ?",
            JOptionPane.INFORMATION_MESSAGE));

        gameMenu.add(newGameItem);
        gameMenu.add(settingsItem);
        gameMenu.add(exitItem);

        helpMenu.add(tutorialItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void initMainPanel() {
        /* Le panel pour les boutons */
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, Rules.COLUMNS, 10, 0));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
        for (JButton button : buttons)
            pnlButtons.add(button);

        /* Le label pour l'image de la grille */
        JLabel lblBoard = new JLabel(BOARD);
        lblBoard.setBounds(20, 20, BOARD.getIconWidth(), BOARD.getIconHeight());

        /* Le panel pour la grille */
        JLayeredPane lpnBoard = new JLayeredPane();
        lpnBoard.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        lpnBoard.add(lblBoard, 0, 1);

        /* Crée les labels correspondant à toutes les cases */
        for (int i = 0; i < Rules.ROWS; ++i)
            for (int j = 0; j < Rules.COLUMNS; ++j) {
                grid[i][j] = new JLabel();
                grid[i][j].setBounds(75 * j + 27, 75 * (Rules.ROWS - i - 1) + 27,
                                     RED.getIconWidth(), RED.getIconHeight());
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
        } while (player1 == null || player1.length() < 1);
        do {
            player2 = (String)promptUserInput("Veuillez entrer le nom du joueur 2 :",
                                              "Nom du joueur 2", null);
        } while (player2 == null || player2.length() < 1);
        do {
            winsNeeded = (Integer)promptUserInput(
                    "Veuillez entrer le nombre de manches nécessaires pour gagner :",
                    "Nombre de manches", IntStream.range(1, 11).boxed().toArray(Integer[]::new));
        } while (winsNeeded == null);

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

        Checker[][] gameGrid = game.getGrid().getGrid();
        for (int i = 0; i < Rules.ROWS; ++i)
            for (int j = 0; j < Rules.COLUMNS; ++j)
                switch (gameGrid[i][j]) {
                    case RED -> grid[i][j].setIcon(RED);
                    case YELLOW -> grid[i][j].setIcon(YELLOW);
                    case NONE -> grid[i][j].setIcon(null);
                }

        if (game.isOver()) {
            Player winner = game.getWinner();
            int choice = JOptionPane.showConfirmDialog(null,
                    winner.getName() + " a gagné ! Faire une revanche ?",
                    "Partie terminée", JOptionPane.YES_NO_OPTION);

            setAllButtonsEnabled(false);
            Arrays.stream(getKeyListeners()).forEach(this::removeKeyListener);

            if (choice == JOptionPane.YES_OPTION)
                newGame(game.getPlayers()[0].getName(),
                        game.getPlayers()[1].getName(),
                        game.getWinsNeeded());
        }
    }

    /**
     * Initialise le "look-and-feel" pour le programme en cours à "GTK+", s'il
     * est disponible.
     */
    private static void initLookAndFeel() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            if (info.getName().equals("GTK+")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
    }
}
