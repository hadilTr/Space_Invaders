package tn.client.space_invaders.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.services.GameLogger;

public class ShieldDecorator extends PlayerDecorator {

    private boolean isBroken = false;

    public ShieldDecorator(Player decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public boolean takeHit() {
        this.isBroken = true;
        GameLogger.getInstance().warning("BOUCLIER CASSÉ ! Dégâts absorbés.");
        return false;
    }

    @Override
    public Player processExpiration() {
        if ((System.currentTimeMillis() - startTime > duration) || isBroken) {
            return decoratedPlayer.processExpiration();
        }

        return super.processExpiration();
    }

    @Override
    public void refreshPowerUp(Class<?> type) {
        if (type.isInstance(this)) {
            this.startTime = System.currentTimeMillis();
            this.isBroken = false;
        } else {
            super.refreshPowerUp(type);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);

        gc.setStroke(Color.CYAN);
        gc.setLineWidth(2);

        long timeLeft = duration - (System.currentTimeMillis() - startTime);
        if (timeLeft < 1000 && timeLeft > 0) { // S'il reste moins d'1 seconde
            if (System.currentTimeMillis() % 200 > 100) return; // Clignote
        }

        gc.strokeOval(getX() - 5, getY() - 5, getWidth() + 10, getHeight() + 10);
    }
}