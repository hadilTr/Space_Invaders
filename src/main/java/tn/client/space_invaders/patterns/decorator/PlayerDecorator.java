package tn.client.space_invaders.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.services.GameLogger;

import java.util.List;

public abstract class PlayerDecorator extends Player {

    protected Player decoratedPlayer;
    protected long startTime;
    protected long duration = 8000;

    public PlayerDecorator(Player decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
        this.game = decoratedPlayer.game;
        this.startTime = System.currentTimeMillis();

        this.x = decoratedPlayer.getX();
        this.y = decoratedPlayer.getY();
        this.width = decoratedPlayer.getWidth();
        this.height = decoratedPlayer.getHeight();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        decoratedPlayer.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        decoratedPlayer.setY(y);
    }

    @Override
    public int getX() { return decoratedPlayer.getX(); }
    @Override
    public int getY() { return decoratedPlayer.getY(); }

    @Override
    public void update() {
        decoratedPlayer.update();
    }

    @Override
    public Player processExpiration() {
        if (System.currentTimeMillis() - startTime > duration) {
            return decoratedPlayer.processExpiration();
        }
        decoratedPlayer = decoratedPlayer.processExpiration();
        return this;
    }

    @Override
    public void refreshPowerUp(Class<?> type) {
        if (type.isInstance(this)) {
            this.startTime = System.currentTimeMillis();
            GameLogger.getInstance().info("Timer réinitialisé pour : " + type.getSimpleName());
        } else {
            decoratedPlayer.refreshPowerUp(type);
        }
    }

    @Override
    public void draw(GraphicsContext gc) { decoratedPlayer.draw(gc); }
    @Override
    public boolean takeHit() { return decoratedPlayer.takeHit(); }
    @Override
    public boolean hasPowerUp(Class<?> type) {
        return type.isInstance(this) || decoratedPlayer.hasPowerUp(type);
    }

    @Override
    public List<Projectile> shoot() { return decoratedPlayer.shoot(); }
    @Override
    public List<Projectile> createProjectiles() { return decoratedPlayer.createProjectiles(); }
}