package tn.client.space_invaders.patterns.decorator;

import javafx.scene.paint.Color;
import tn.client.space_invaders.model.GameComponent;
import javafx.scene.canvas.GraphicsContext;


public class DoubleGunDecorator extends PlayerDecorator {

    public DoubleGunDecorator(GameComponent decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void draw(GraphicsContext g) {
        super.draw(g);
        // Dessiner un deuxième canon
        g.setFill(Color.RED);
        g.fillRect(getX() + getWidth() / 2 - 5, getY() - 10, 10, 10);
    }
}