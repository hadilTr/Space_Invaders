package tn.client.space_invaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile extends GameObject {

    private int dy;
    private boolean active = true;
    private boolean enemyShot;

    public Projectile(int x, int y, int dy, boolean isEnemy) {
        super(x, y, 5, 10);
        this.dy = dy;
        this.enemyShot = isEnemy;
    }

    @Override
    public void update() {
        y += dy;

        if (y < 0 || y > 600) {
            active = false;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(enemyShot ? Color.RED : Color.YELLOW);
        gc.fillRect(x, y, width, height);
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public boolean isEnemyShot() { return enemyShot; }
}