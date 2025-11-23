package tn.client.space_invaders.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.model.GameObject;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.patterns.factory.EntityFactory;

public class Player extends GameObject {

    private Game game;
    private int speed = 5;
    private long lastShotTime = 0;
    private long shootCooldown = 500;

    // Power-up effects
    private boolean hasRapidFire = false;
    private boolean hasShield = false;
    private boolean hasTripleShot = false;
    private boolean hasSpeedBoost = false;

    private long rapidFireEndTime = 0;
    private long shieldEndTime = 0;
    private long tripleShotEndTime = 0;
    private long speedBoostEndTime = 0;

    public Player(Game game, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.game = game;
    }

    @Override
    public void update() {
        // Update power-up timers
        long now = System.currentTimeMillis();
        if (hasRapidFire && now > rapidFireEndTime) hasRapidFire = false;
        if (hasShield && now > shieldEndTime) hasShield = false;
        if (hasTripleShot && now > tripleShotEndTime) hasTripleShot = false;
        if (hasSpeedBoost && now > speedBoostEndTime) hasSpeedBoost = false;

        // Calculate current speed
        int currentSpeed = hasSpeedBoost ? speed * 2 : speed;

        // Movement
        if (game.getInputHandler().isKeyPressed(KeyCode.LEFT)) {
            x -= currentSpeed;
        }
        if (game.getInputHandler().isKeyPressed(KeyCode.RIGHT)) {
            x += currentSpeed;
        }

        // Boundaries
        if (x < 0) x = 0;
        if (x > Game.WIDTH - width) x = Game.WIDTH - width;
    }

    public Projectile shoot() {
        long now = System.currentTimeMillis();
        long cooldown = hasRapidFire ? shootCooldown / 3 : shootCooldown;

        if (now - lastShotTime > cooldown) {
            lastShotTime = now;
            return EntityFactory.createProjectile(x + width / 2 - 2, y);
        }
        return null;
    }

    public Projectile[] shootTriple() {
        if (!hasTripleShot) return null;

        long now = System.currentTimeMillis();
        long cooldown = hasRapidFire ? shootCooldown / 3 : shootCooldown;

        if (now - lastShotTime > cooldown) {
            lastShotTime = now;
            return new Projectile[] {
                    EntityFactory.createProjectile(x + width / 2 - 2, y),           // Center
                    EntityFactory.createProjectile(x + width / 4 - 2, y),           // Left
                    EntityFactory.createProjectile(x + width * 3 / 4 - 2, y)        // Right
            };
        }
        return null;
    }

    public void activatePowerUp(String type, int duration) {
        long endTime = System.currentTimeMillis() + duration;

        switch (type) {
            case "RAPID_FIRE":
                hasRapidFire = true;
                rapidFireEndTime = endTime;
                break;
            case "SHIELD":
                hasShield = true;
                shieldEndTime = endTime;
                break;
            case "TRIPLE_SHOT":
                hasTripleShot = true;
                tripleShotEndTime = endTime;
                break;
            case "SPEED_BOOST":
                hasSpeedBoost = true;
                speedBoostEndTime = endTime;
                break;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Draw shield effect if active
        if (hasShield) {
            gc.setStroke(Color.CYAN);
            gc.setLineWidth(3);
            gc.strokeOval(x - 5, y - 5, width + 10, height + 10);
        }

        // Spacecraft design
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

        // Engine color changes with speed boost
        gc.setFill(hasSpeedBoost ? Color.YELLOW : Color.ORANGE);
        gc.fillOval(x + width * 0.13, y + height * 0.95, width * 0.14, height * 0.1);
        gc.fillOval(x + width * 0.73, y + height * 0.95, width * 0.14, height * 0.1);
        gc.fillOval(x + width * 0.38, y + height * 0.98, width * 0.24, height * 0.08);

        gc.setFill(Color.WHITE);
        gc.fillRect(x + width * 0.43, y + height * 0.5, width * 0.04, height * 0.15);
        gc.fillRect(x + width * 0.53, y + height * 0.5, width * 0.04, height * 0.15);

        gc.setFill(Color.NAVY);
        gc.fillRect(x + width * 0.05, y + height * 0.65, width * 0.08, height * 0.08);
        gc.fillRect(x + width * 0.87, y + height * 0.65, width * 0.08, height * 0.08);
    }

    public boolean hasShield() {
        return hasShield;
    }

    public boolean hasTripleShot() {
        return hasTripleShot;
    }
}