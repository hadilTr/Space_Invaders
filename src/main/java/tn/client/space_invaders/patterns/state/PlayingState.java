package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.model.Level;
import tn.client.space_invaders.model.PowerUp;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.patterns.composite.EnemyGroup;
import tn.client.space_invaders.patterns.decorator.Player;
import tn.client.space_invaders.patterns.factory.EntityFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayingState implements GameState {

    private Game game;
    private Player player;
    private EnemyGroup enemies;

    private int currentLevel = 0;
    private List<Level> levels;

    private long lastEscTime = 0;
    private List<Projectile> projectiles = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();

    private boolean loadNextLevel = false;

    public PlayingState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        if (levels == null) {
            levels = game.initializeLevels();
        }

        Level level = levels.get(currentLevel);

        enemies = new EnemyGroup();
        projectiles.clear();

        if (player == null) {
            System.out.println("Starting Game - Level " + (currentLevel + 1));
            player = (Player) EntityFactory.createPlayer(game, 375, 500, 50, 50);
        } else {
            System.out.println("Loading Level " + (currentLevel + 1));
        }

        for (int row = 0; row < level.numRows; row++) {
            for (int col = 0; col < level.numCols; col++) {
                enemies.add(EntityFactory.createEnemy(
                        level.startX + col * level.colSpacing,
                        level.startY + row * level.rowSpacing,
                        40, 30));
            }
        }

        lastEscTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (loadNextLevel) {
            loadNextLevel = false;
            enter();
            return;
        }

        if (game.getInputHandler().isKeyPressed(KeyCode.ESCAPE)) {
            long now = System.currentTimeMillis();
            if (now - lastEscTime > 200) {
                lastEscTime = now;
                game.changeState(new PauseState(game, this));
                return;
            }
        }

        if (player != null) player.update();
        if (enemies != null) enemies.update();

        // Player shooting
        if (game.getInputHandler().isKeyPressed(KeyCode.SPACE)) {
            if (player.hasTripleShot()) {
                Projectile[] triple = player.shootTriple();
                if (triple != null) {
                    for (Projectile p : triple) {
                        projectiles.add(p);
                    }
                }
            } else {
                Projectile p = player.shoot();
                if (p != null) projectiles.add(p);
            }
        }

        // Enemies shooting
        Level level = levels.get(currentLevel);
        if (Math.random() < level.enemyFireRate) {
            Projectile p = enemies.shootRandom();
            if (p != null) projectiles.add(p);
        }

        // Update and check projectiles
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.update();

            if (!p.isEnemyShot()) {
                if (enemies.checkCollision(p)) {
                    p.setActive(false);
                    game.addScore(level.scorePerEnemy);

                    // 20% chance to drop power-up
                    if (Math.random() < 0.9) {
                        spawnPowerUp((int)p.getX(), (int)p.getY());
                    }
                }
            } else {
                if (!player.hasShield() && checkPlayerCollision(p)) {
                    p.setActive(false);
                    System.out.println("GAME OVER");
                    game.changeState(new MenuState(game));
                }
            }

            if (!p.isActive()) it.remove();
        }

        // Update power-ups
        Iterator<PowerUp> powerUpIt = powerUps.iterator();
        while (powerUpIt.hasNext()) {
            PowerUp powerUp = powerUpIt.next();
            powerUp.update();

            if (powerUp.checkCollision(player)) {
                activatePowerUp(powerUp.getType());
                powerUp.setActive(false);
                System.out.println("Power-up collected: " + powerUp.getType());
            }

            if (!powerUp.isActive()) powerUpIt.remove();
        }

        // Check if level completed
        if (enemies.isEmpty()) {
            currentLevel++;

            if (currentLevel >= levels.size()) {
                System.out.println("YOU WIN THE GAME!!!");
                game.changeState(new MenuState(game));
            } else {
                System.out.println("LEVEL CLEARED! GOING TO LEVEL " + (currentLevel + 1));
                loadNextLevel = true;
            }
        }
    }

    private void spawnPowerUp(int x, int y) {
        PowerUp.PowerUpType[] types = PowerUp.PowerUpType.values();
        PowerUp.PowerUpType randomType = types[(int)(Math.random() * types.length)];
        powerUps.add(new PowerUp(x, y, randomType));
    }

    private void activatePowerUp(PowerUp.PowerUpType type) {
        int duration = 10000; // 10 seconds
        player.activatePowerUp(type.name(), duration);
    }

    private boolean checkPlayerCollision(Projectile p) {
        return p.getX() < player.getX() + player.getWidth() &&
                p.getX() + p.getWidth() > player.getX() &&
                p.getY() < player.getY() + player.getHeight() &&
                p.getY() + p.getHeight() > player.getY();
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (player != null) player.draw(gc);
        if (enemies != null) enemies.draw(gc);

        for (Projectile p : projectiles) {
            p.draw(gc);
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.draw(gc);
        }

        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + game.getScore(), 10, 20);
        gc.fillText("Level: " + (currentLevel + 1), 10, 40);
    }

    @Override
    public void exit() { }
}