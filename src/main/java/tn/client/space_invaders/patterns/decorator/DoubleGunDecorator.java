package tn.client.space_invaders.patterns.decorator;

import tn.client.space_invaders.model.GameComponent;

import java.awt.*;

public class DoubleGunDecorator extends PlayerDecorator {

    public DoubleGunDecorator(GameComponent decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Dessiner un deuxième canon
        g.setColor(Color.RED);
        g.fillRect(getX() + getWidth() / 2 - 5, getY() - 10, 10, 10);
    }
}