package a31.connect4.view;

import a31.connect4.model.Rules;

import javax.swing.*;

final class ResourceLoader {
    static final ImageIcon RED = new ImageIcon(MainWindow.class.getResource("/Red.png"));
    static final ImageIcon YELLOW = new ImageIcon(MainWindow.class.getResource("/Yellow.png"));

    static ImageIcon getBoardImageIcon() {
        return new ImageIcon(MainWindow.class.getResource(String.format("/Board%dx%d.png",
                                                          Rules.COLUMNS,
                                                          Rules.ROWS)));
    }

    /** Rend la classe non instantiable. */
    private ResourceLoader() {}
}
