package fr.unistra.iutrs.a31.connect4;

import fr.unistra.iutrs.a31.connect4.view.MainWindow;

import javax.swing.*;

public class Connect4 {
    public static void main(String[] args) {
        initLookAndFeel();
        new MainWindow();
    }

    /**
     * Initialise le "look-and-feel" pour le programme en cours à "GTK+", s'il
     * est disponible ; sinon, utilise l'apparence système.
     */
    private static void initLookAndFeel() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            if (info.getName().equals("GTK+")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException ignored) {}
                break;
            }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ignored) {}
    }
}
