package a31.connect4;

import a31.connect4.controller.Game;
import a31.connect4.view.MainWindow;

public class Connect4 {
    public static void main(String[] args) {
        Game game=new Game("emile","jules",4);
        new MainWindow(game);
    }
}
