package tn.client.space_invaders.view;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel panel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Space Invaders");
        setResizable(false);

        add(panel);

        pack(); // Ajuste la taille de la fenêtre au contenu
        setLocationRelativeTo(null); // Centre la fenêtre
        setVisible(true);
    }
}