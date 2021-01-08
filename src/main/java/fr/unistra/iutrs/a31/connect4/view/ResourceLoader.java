package fr.unistra.iutrs.a31.connect4.view;

import fr.unistra.iutrs.a31.connect4.model.Rules;

import java.awt.*;
import javax.swing.*;

final class ResourceLoader {
    static final Image ICON = new ImageIcon(MainWindow.class.getResource("/Icon.png")).getImage();
    static final ImageIcon RED = new ImageIcon(MainWindow.class.getResource("/Red.png"));
    static final ImageIcon YELLOW = new ImageIcon(MainWindow.class.getResource("/Yellow.png"));

    static ImageIcon getBoardImageIcon() {
        return new ImageIcon(MainWindow.class.getResource(String.format("/Board%dx%d.png",
                                                          Rules.getColumns(),
                                                          Rules.getRows())));
    }

    /** Rend la classe non instantiable. */
    private ResourceLoader() {}
}
