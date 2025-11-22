package tn.client.space_invaders.patterns.decorator;

import tn.client.space_invaders.model.GameComponent;
import javafx.scene.canvas.GraphicsContext;

public abstract class PlayerDecorator implements GameComponent {

    protected GameComponent decoratedPlayer;

    public PlayerDecorator(GameComponent decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
    }

    @Override
    public void update() {
        decoratedPlayer.update();
    }

    @Override
    public void draw(GraphicsContext g) {
        decoratedPlayer.draw(g);
    }

    @Override
    public int getX() {
        return decoratedPlayer.getX();
    }

    @Override
    public int getY() {
        return decoratedPlayer.getY();
    }

    @Override
    public int getWidth() {
        return decoratedPlayer.getWidth();
    }

    @Override
    public int getHeight() {
        return decoratedPlayer.getHeight();
    }
}