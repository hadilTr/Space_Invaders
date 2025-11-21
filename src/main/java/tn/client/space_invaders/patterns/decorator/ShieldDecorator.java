package tn.client.space_invaders.patterns.decorator;

import tn.client.space_invaders.model.GameComponent;

import java.awt.*;

public class ShieldDecorator extends PlayerDecorator {

    public ShieldDecorator(GameComponent decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Dessiner un bouclier autour du joueur
        g.setColor(Color.CYAN);
        g.drawRect(getX() - 5, getY() - 5, getWidth() + 10, getHeight() + 10);
    }
}