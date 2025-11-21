package tn.client.space_invaders.patterns.decorator;

import tn.client.space_invaders.model.GameObject;

import java.awt.*;

public class Player extends GameObject {

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        // Mouvement du joueur
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}