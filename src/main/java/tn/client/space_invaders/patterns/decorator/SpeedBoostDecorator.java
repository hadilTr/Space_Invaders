package tn.client.space_invaders.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig;

public class SpeedBoostDecorator extends PlayerDecorator {

    public SpeedBoostDecorator(Player decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void update() {
        int currentX = getX();
        int boostedSpeed = 10;

        if (game.getInputHandler().isActionActive(GameConfig.Action.LEFT)) {
            currentX -= boostedSpeed;
        }
        if (game.getInputHandler().isActionActive(GameConfig.Action.RIGHT)) {
            currentX += boostedSpeed;
        }

        if (currentX < 0) currentX = 0;
        if (currentX > Game.WIDTH - getWidth()) currentX = Game.WIDTH - getWidth();

        setX(currentX);

    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.setFill(Color.ORANGE);
        gc.fillRect(getX(), getY() + getHeight(), getWidth(), 5);
    }
}