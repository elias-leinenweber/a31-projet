package a31.connect4.view;

import a31.connect4.controller.Game;
import a31.connect4.model.Checker;
import a31.connect4.model.Grid;
import a31.connect4.model.Player;
import a31.connect4.model.Rules;
import a31.observer.Observer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JFrame implements Observer {
    static final int DEFAULT_WIDTH = 570;
    static final int DEFAULT_HEIGHT = 525;
    static Grid board;
    private Game game;
    static JFrame frameMainWindow;
    static JPanel panelMain;
    static JPanel panelBoardNumbers;
    private JLayeredPane layeredGameBoard;
    static JButton[] buttons;
    static int row, col;

    // Player 1 symbol: X. Plays first.
    // Player 2 symbol: O.
    static JLabel turnMessage;

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

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
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

    public JLayeredPane createLayeredBoard() {
        layeredGameBoard = new JLayeredPane();
        layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect-4"));

        ImageIcon imageBoard = new ImageIcon(MainWindow.class.getResource("/Board.png"));
        JLabel imageBoardLabel = new JLabel(imageBoard);

        imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
        layeredGameBoard.add(imageBoardLabel, 0, 1);
        return layeredGameBoard;
    }

    // To be called when the game starts for the first time
    // or a new game starts.
    public void createNewGame() {
        setAllButtonsEnabled(true);

        board = new Grid(Rules.ROWS, Rules.COLUMNS);

        if (frameMainWindow != null)
            frameMainWindow.dispose();
        // make the main window appear on the center
        Component compMainWindowContents = createContentComponents();
        frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

        if (frameMainWindow.getKeyListeners().length == 0) {
            frameMainWindow.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    int col = Integer.parseInt(KeyEvent.getKeyText(e.getKeyCode()));

                    makeMove(col - 1);
                    if (!board.isOverflow())
                        game();
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });
        }

        frameMainWindow.setFocusable(true);

        // show window
        frameMainWindow.pack();
        // Makes the board visible before adding menus.
        // frameMainWindow.setVisible(true);

        // Add the turn label.
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        frameMainWindow.add(tools, BorderLayout.PAGE_END);
        turnMessage = new JLabel("Turn: " + game.getTurn());
        tools.add(turnMessage);

        addMenus();

        System.out.println("Turn: " + game.getTurn());
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
    public void makeMove(int col) {
        row = game.play(game.getCurrentPlayer(), col);
        MainWindow.col = col;
    }

    // It places a checker on the board.
    public void placeChecker(Color color, int row, int col) {
        String colorString = String.valueOf(color).charAt(0) + String.valueOf(color).toLowerCase().substring(1);
        int xOffset = 75 * col;
        int yOffset = 75 * row;
        ImageIcon checkerIcon = new ImageIcon(MainWindow.class.getResource(colorString + ".png"));

        JLabel checkerLabel = new JLabel(checkerIcon);
        checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(), checkerIcon.getIconHeight());
        layeredGameBoard.add(checkerLabel, 0, 0);
    }

    public void game() {
        turnMessage.setText("Turn: " + game.getTurn());

        Checker playerColor = game.getCurrentPlayer().getColor();
        Color color = Color.GRAY;
        if (playerColor == Checker.RED)
            color = Color.RED;
        else if (playerColor == Checker.YELLOW)
            color = Color.YELLOW;
        placeChecker(color, row, col);

        System.out.println("Turn: " + game.getTurn());

        if (game.isOver())
            gameOver();
    }

    public void setAllButtonsEnabled(boolean b) {
        if (b) {
            for (int i = 0; i < buttons.length; i++) {
                JButton button = buttons[i];
                int column = i;

                if (button.getActionListeners().length == 0) {
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            makeMove(column);

                            if (!board.isOverflow())
                                game();
                            frameMainWindow.requestFocusInWindow();
                        }
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
        panelBoardNumbers = new JPanel();
        panelBoardNumbers.setLayout(new GridLayout(1, Rules.COLUMNS, Rules.ROWS, 4));
        panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));

        for (JButton button : buttons)
            panelBoardNumbers.add(button);

        // main Connect-4 board creation
        layeredGameBoard = createLayeredBoard();

        // panel creation to store all the elements of the board
        panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // add button and main board components to panelMain
        panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
        panelMain.add(layeredGameBoard, BorderLayout.CENTER);

        frameMainWindow.setResizable(false);
        return panelMain;
    }

    // It gets called only of the game is over.
    // We can check if the game is over by calling the method "checkGameOver()"
    // of the class "Board".
    public void gameOver() {
        int choice = 0;
        Player winner = game.getWinner();
        if (winner != null)
            choice = JOptionPane.showConfirmDialog(null, winner.getName() + " wins! Start a new game?", "Game Over", JOptionPane.YES_NO_OPTION);
        else
            choice = JOptionPane.showConfirmDialog(null, "It's a draw! Start a new game?", "Game Over", JOptionPane.YES_NO_OPTION);

        // Disable buttons
        setAllButtonsEnabled(false);

        // Remove key listener
        for (KeyListener keyListener : frameMainWindow.getKeyListeners())
            frameMainWindow.removeKeyListener(keyListener);

        if (choice == JOptionPane.YES_OPTION)
            createNewGame();
    }

    @Override
    public void update() {
    }
}
