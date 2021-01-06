package a31.connect4.view;

import a31.connect4.controller.Game;
import a31.connect4.model.Checker;
import a31.connect4.model.Grid;
import a31.connect4.model.Player;
import a31.connect4.model.Rules;
import a31.observer.Observer;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class MainWindow extends JFrame implements Observer {
    private static final ImageIcon BOARD    = new ImageIcon(MainWindow.class.getResource("/Board.png"));
    private static final ImageIcon RED      = new ImageIcon(MainWindow.class.getResource("/Red.png"));
    private static final ImageIcon YELLOW   = new ImageIcon(MainWindow.class.getResource("/Yellow.png"));
    private static final int DEFAULT_WIDTH  = 570;
    private static final int DEFAULT_HEIGHT = 525;

    private Game game;
    private final JButton[] buttons;
    private final JLabel[][] grid;
    private final JLabel lblStatus;

    public MainWindow() {
        super("Puissance 4");
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
        getContentPane().add(createContentComponents(), BorderLayout.CENTER);

        JToolBar barStatus = new JToolBar();
        barStatus.setFloatable(false);

        lblStatus = new JLabel("Tour n°");
        barStatus.add(lblStatus);
        add(barStatus, BorderLayout.PAGE_END);

        /* Écouteur de touches (raccourcis clavier pour les boutons) */
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                makeMove(Integer.parseInt(KeyEvent.getKeyText(e.getKeyCode())) - 1);
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        centerWindow(this, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Partie");
        JMenuItem newGameItem = new JMenuItem("Nouvelle partie");
        JMenuItem settingsItem = new JMenuItem("Options");
        JMenuItem exitItem = new JMenuItem("Quitter");

        JMenu helpMenu = new JMenu("?");
        JMenuItem tutorialItem = new JMenuItem("Tutoriel");

        /* Écouteurs d'événements. */
        newGameItem.addActionListener(e -> newGame());
        settingsItem.addActionListener(e -> new SettingsWindow());
        exitItem.addActionListener(e -> System.exit(0));

        /* TODO Traduire le tutoriel en français. */
        tutorialItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
            "Click on the buttons or press 1-" + Rules.COLUMNS + "on your " +
            "keyboard to insert a new checker.\nTo win you must place " +
            Rules.IN_A_ROW + " checkers in an row, horizontally, vertically " +
            "or diagonally.", "How to Play", JOptionPane.INFORMATION_MESSAGE));

        gameMenu.add(newGameItem);
        gameMenu.add(settingsItem);
        gameMenu.add(exitItem);

        helpMenu.add(tutorialItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void newGame() {
        /*
         * TODO Fenêtre de dialogue qui demande les noms des joueurs et le
         * nombre de manches à jouer.
         */
        newGame("Alice", "Bob", 1);
    }

    private void newGame(String player1, String player2, int winsNeeded) {
        game = new Game(player1, player2, winsNeeded);
        game.register(this);

        /* TODO Réinitialiser l'interface. */
        setAllButtonsEnabled(true);
    }

    private void makeMove(int col) {
        game.play(game.getCurrentPlayer(), col);
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
        } else
            for (JButton button : buttons) {
                button.setEnabled(false);
                for (ActionListener actionListener : button.getActionListeners())
                    button.removeActionListener(actionListener);
            }
    }

    /**
     * It returns a component to be drawn by main window. This function creates the main window
     * components. It calls the "actionListener" function, when a click on a button is made.
     */
    private Component createContentComponents() {

        // Create a panel to set up the board buttons.
        JPanel panelBoardNumbers = new JPanel();
        panelBoardNumbers.setLayout(new GridLayout(1, Rules.COLUMNS, Rules.ROWS, 4));
        panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));

        Arrays.stream(buttons).forEach(panelBoardNumbers::add);

        // main Connect-4 board creation
        JLayeredPane layeredGameBoard = new JLayeredPane();
        layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Puissance 4"));
        JLabel imageBoardLabel = new JLabel(BOARD);
        imageBoardLabel.setBounds(20, 20, BOARD.getIconWidth(), BOARD.getIconHeight());
        layeredGameBoard.add(imageBoardLabel, 0, 1);

        for (int i = 0; i < Rules.ROWS; ++i)
            for (int j = 0; j < Rules.COLUMNS; ++j) {
                grid[i][j] = new JLabel();
                grid[i][j].setBounds(75 * j + 27, 75 * (Rules.ROWS - i - 1) + 27, RED.getIconWidth(), RED.getIconHeight());
                layeredGameBoard.add(grid[i][j], 0, 0);
            }

        // panel creation to store all the elements of the board
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // add button and main board components to panelMain
        panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
        panelMain.add(layeredGameBoard, BorderLayout.CENTER);

        setResizable(false);
        return panelMain;
    }

    @Override
    public void update() {
        lblStatus.setText("Tour n° " + game.getTurn() + " (" + game.getCurrentPlayer().getName() + ")");

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
            int choice = JOptionPane.showConfirmDialog(null, (winner != null) ?
                            (winner.getName() + " wins! Start a new game?") :
                            "It's a draw! Start a new game?",
                            "Game Over", JOptionPane.YES_NO_OPTION);

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

    /**
     * Centre la fenêtre sur l'écran.
     *
     * @param frame  la fenêtre à centrer
     * @param width  la largeur de la fenêtre
     * @param height la hauteur de la fenêtre
     */
    private static void centerWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - frame.getWidth() - width) / 2;
        int y = (int) (dimension.getHeight() - frame.getHeight() - height) / 2;
        frame.setLocation(x, y);
    }
}
