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
    private Grid board;
    private final JButton[] buttons;
    private JLabel turnMessage;
    private JLayeredPane layeredGameBoard;
    private int col, row;

    public MainWindow() {
        super("Puissance 4");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        addMenus();

        buttons = new JButton[Rules.COLUMNS];
        for (int i = 0; i < Rules.COLUMNS; i++) {
            buttons[i] = new JButton(i + 1 + "");
            buttons[i].setFocusable(false);
        }

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        centerWindow(this, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        createNewGame();
    }

    private void addMenus() {
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
        game = new Game("Alice", "Bob", 1);
        game.register(this);
    }

    private void createNewGame() {
        setAllButtonsEnabled(true);

        board = new Grid(Rules.ROWS, Rules.COLUMNS);
        newGame();

        Component compMainWindowContents = createContentComponents();
        getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

        if (getKeyListeners().length == 0) {
            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    makeMove(Integer.parseInt(KeyEvent.getKeyText(e.getKeyCode())) - 1);
                    if (!board.isOverflow())
                        game();
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });
        }

        setFocusable(true);

        pack();
        // Makes the board visible before adding menus.
        // frameMainWindow.setVisible(true);

        // Add the turn label.
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        add(tools, BorderLayout.PAGE_END);
        turnMessage = new JLabel("Turn: " + game.getTurn());
        tools.add(turnMessage);

        addMenus();
    }

    /**
     * Centre la fenêtre sur l'écran.
     *
     * @param frame  la fenêtre à centrer
     * @param width  la largeur de la fenêtre
     * @param height la hauteur de la fenêtre
     */
    public static void centerWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - frame.getWidth() - width) / 2;
        int y = (int) (dimension.getHeight() - frame.getHeight() - height) / 2;
        frame.setLocation(x, y);
    }

    // It finds which player plays next and makes a move on the board.
    private void makeMove(int col) {
        row = game.play(game.getCurrentPlayer(), col);
        this.col = col;
    }

    /**
     * Insère un jeton dans une case donnée.
     *
     * @param checker le jeton à insérer
     * @param row la ligne dans laquelle insérer le jeton
     * @param col la colonne dans laquelle insérer le jeton
     */
    private void placeChecker(Checker checker, int row, int col) {
        ImageIcon imgChecker = checker == Checker.RED ? RED : YELLOW;
        JLabel checkerLabel = new JLabel(imgChecker);

        checkerLabel.setBounds(75 * col + 27, 75 * row + 27, imgChecker.getIconWidth(), imgChecker.getIconHeight());
        layeredGameBoard.add(checkerLabel, 0, 0);
    }

    private void game() {
        turnMessage.setText("Tour : " + game.getTurn());
        placeChecker(game.getCurrentPlayer().getColor(), row, col);
        if (game.isOver())
            gameOver();
    }

    private void setAllButtonsEnabled(boolean b) {
        if (b) {
            for (int i = 0; i < buttons.length; i++) {
                JButton button = buttons[i];
                int column = i;

                if (button.getActionListeners().length == 0) {
                    button.addActionListener(e -> {
                        makeMove(column);

                        if (!board.isOverflow())
                            game();
                        requestFocusInWindow();
                    });
                }
            }
        } else
            for (JButton button : buttons)
                for (ActionListener actionListener : button.getActionListeners())
                    button.removeActionListener(actionListener);
    }

    /**
     * It returns a component to be drawn by main window. This function creates the main window
     * components. It calls the "actionListener" function, when a click on a button is made.
     */
    public Component createContentComponents() {
        // Create a panel to set up the board buttons.
        JPanel panelBoardNumbers = new JPanel();
        panelBoardNumbers.setLayout(new GridLayout(1, Rules.COLUMNS, Rules.ROWS, 4));
        panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));

        for (JButton button : buttons)
            panelBoardNumbers.add(button);

        // main Connect-4 board creation
        layeredGameBoard = new JLayeredPane();
        layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Puissance 4"));
        JLabel imageBoardLabel = new JLabel(BOARD);
        imageBoardLabel.setBounds(20, 20, BOARD.getIconWidth(), BOARD.getIconHeight());
        layeredGameBoard.add(imageBoardLabel, 0, 1);

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

    public void gameOver() {
        Player winner = game.getWinner();
        int choice = JOptionPane.showConfirmDialog(null, (winner != null) ?
                (winner.getName() + " wins! Start a new game?") :
                "It's a draw! Start a new game?",
                "Game Over", JOptionPane.YES_NO_OPTION);

        setAllButtonsEnabled(false);
        Arrays.stream(getKeyListeners()).forEach(this::removeKeyListener);

        if (choice == JOptionPane.YES_OPTION)
            createNewGame();
    }

    @Override
    public void update() {
    }
}
