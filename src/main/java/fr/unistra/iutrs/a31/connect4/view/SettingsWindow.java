package fr.unistra.iutrs.a31.connect4.view;

import fr.unistra.iutrs.a31.connect4.model.Rules;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JDialog {
    public SettingsWindow(JFrame caller) {
        super(caller, "Options", true);

        JButton btnFourInARow = new JButton("Original Connect 4");
        btnFourInARow.addActionListener(e -> {
            Rules.fourInARow();
            caller.dispose();
            new MainWindow();
            dispose();
        });

        JButton btnFiveInARow = new JButton("5-in-a-Row");
        btnFiveInARow.addActionListener(e -> {
            Rules.fiveInARow();
            caller.dispose();
            new MainWindow();
            dispose();
        });

        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(2, 1, 0, 10));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlButtons.add(btnFourInARow);
        pnlButtons.add(btnFiveInARow);

        add(pnlButtons);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
