package a31.connect4.view;

import javax.swing.*;

public class SettingsWindow extends JFrame {
    public SettingsWindow() {
        super("Options");
        JButton jbConnect5=new JButton("Connect5");
        jbConnect5.setSize(30,40);
        add(jbConnect5);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
