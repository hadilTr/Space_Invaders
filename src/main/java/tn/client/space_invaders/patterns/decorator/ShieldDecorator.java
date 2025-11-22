package tn.client.space_invaders.patterns.decorator;

import tn.client.space_invaders.model.GameComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShieldDecorator extends PlayerDecorator {

    public ShieldDecorator(GameComponent decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);

        gc.setStroke(Color.CYAN);
        gc.setLineWidth(2);

        gc.strokeRect(getX() - 5, getY() - 5, getWidth() + 10, getHeight() + 10);
    }
}