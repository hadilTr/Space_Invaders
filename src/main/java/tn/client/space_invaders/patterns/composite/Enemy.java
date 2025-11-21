package tn.client.space_invaders.patterns.composite;

import tn.client.space_invaders.model.GameObject;

import java.awt.*;

public class Enemy extends GameObject {

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        // Mouvement de l'ennemi
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}