package a31.connect4.view;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JFrame {
    public SettingsWindow() {
        super("Options");
        JButton jb5Row=new JButton("5-in-a-row");
        JButton jbHello=new JButton("Hello");


        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(2, 1, 10, 0));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(10, 5, 20, 5));
        pnlButtons.add(jb5Row);
        pnlButtons.add(jbHello);

        add(pnlButtons);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(200,150);
        setVisible(true);

        //jbConnect5.addActionListener(e->{});
        jbHello.addActionListener(e->{});
        }
    }

