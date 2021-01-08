package a31.connect4.view;

import a31.connect4.model.Rules;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends Connect4Window {
    public SettingsWindow(JFrame caller) {
        super("Options");

        JButton btnFourInARow = new JButton("4-in-a-row");
        btnFourInARow.addActionListener(e -> {
            Rules.fourInARow();
            caller.dispose();
            new MainWindow();
            dispose();
        });

        JButton btnFiveInARow = new JButton("5-in-a-row");
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
