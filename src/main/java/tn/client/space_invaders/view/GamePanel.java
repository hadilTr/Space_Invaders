package tn.client.space_invaders.view;

import tn.client.space_invaders.core.Game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addKeyListener(new tn.client.space_invaders.controller.InputHandler(game));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game != null) {
            game.draw(g);
        }
    }
}