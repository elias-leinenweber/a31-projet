package a31.connect4.view;

import javax.swing.*;

public abstract class Connect4Window extends JFrame {
    public Connect4Window(String title) {
        super(title);
        setIconImage(ResourceLoader.ICON);
    }
}
