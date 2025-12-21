package tn.client.space_invaders.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig;
import tn.client.space_invaders.model.GameObject;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.patterns.factory.EntityFactory;

import java.util.Collections;
import java.util.List;

public class Player extends GameObject {

    protected Game game;
    protected int speed = 5;

    protected long lastShotTime = 0;
    protected long shootCooldown = 500;

    protected Player() { super(0, 0, 0, 0); }

    public Player(Game game, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.game = game;
    }

    @Override
    public void update() {
        if (game == null) return;
        if (game.getInputHandler().isActionActive(GameConfig.Action.LEFT)) x -= speed;
        if (game.getInputHandler().isActionActive(GameConfig.Action.RIGHT)) x += speed;
        if (x < 0) x = 0;
        if (x > Game.WIDTH - width) x = Game.WIDTH - width;
    }


    public List<Projectile> shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime > shootCooldown) {
            lastShotTime = now;
            return createProjectiles();
        }
        return Collections.emptyList();
    }

    public List<Projectile> createProjectiles() {
        return Collections.singletonList(
                EntityFactory.createProjectile((int)(x + width / 2 - 2), (int)y)
        );
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public Player processExpiration() { return this; }
    public boolean takeHit() { return true; }
    public boolean hasPowerUp(Class<?> type) { return false; }
    public Player getBasePlayer() { return this; }
    public void refreshPowerUp(Class<?> type) {}

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.CYAN);
        gc.fillRect(x + width * 0.35, y + height * 0.3, width * 0.3, height * 0.6);

        gc.setFill(Color.LIGHTBLUE);
        double[] cockpitX = {x + width * 0.5, x + width * 0.35, x + width * 0.65};
        double[] cockpitY = {y, y + height * 0.4, y + height * 0.4};
        gc.fillPolygon(cockpitX, cockpitY, 3);

        gc.setFill(Color.DARKBLUE);
        gc.fillOval(x + width * 0.42, y + height * 0.15, width * 0.16, height * 0.2);

        gc.setFill(Color.DEEPSKYBLUE);
        double[] leftWingX = {x, x + width * 0.35, x + width * 0.35, x + width * 0.1};
        double[] leftWingY = {y + height * 0.6, y + height * 0.4, y + height * 0.7, y + height * 0.8};
        gc.fillPolygon(leftWingX, leftWingY, 4);

        double[] rightWingX = {x + width, x + width * 0.65, x + width * 0.65, x + width * 0.9};
        double[] rightWingY = {y + height * 0.6, y + height * 0.4, y + height * 0.7, y + height * 0.8};
        gc.fillPolygon(rightWingX, rightWingY, 4);

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(x + width * 0.15, y + height * 0.85, width * 0.1, height * 0.15);
        gc.fillRect(x + width * 0.75, y + height * 0.85, width * 0.1, height * 0.15);
        gc.fillRect(x + width * 0.4, y + height * 0.9, width * 0.2, height * 0.1);

        gc.setFill(Color.ORANGE);
        gc.fillOval(x + width * 0.13, y + height * 0.95, width * 0.14, height * 0.1);
        gc.fillOval(x + width * 0.73, y + height * 0.95, width * 0.14, height * 0.1);
        gc.fillOval(x + width * 0.38, y + height * 0.98, width * 0.24, height * 0.08);

        gc.setFill(Color.WHITE);
        gc.fillRect(x + width * 0.43, y + height * 0.5, width * 0.04, height * 0.15);
        gc.fillRect(x + width * 0.53, y + height * 0.5, width * 0.04, height * 0.15);
    }
    }
