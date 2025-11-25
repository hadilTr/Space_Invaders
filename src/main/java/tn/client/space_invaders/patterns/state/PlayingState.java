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
import tn.client.space_invaders.services.SoundManager;
import tn.client.space_invaders.view.HUD;
import tn.client.space_invaders.core.GameConfig;
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
    private HUD hud = new HUD();

    private boolean loadNextLevel = false;

    public PlayingState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        // Chargement initial des niveaux si besoin
        if (levels == null) {
            levels = game.initializeLevels();
        }

        // --- CORRECTION CRUCIALE ---
        // Si le joueur existe ET qu'il reste des ennemis, c'est qu'on revient de PAUSE.
        // On ne recharge PAS le niveau.
        if (player != null && enemies != null && !enemies.isEmpty()) {
            System.out.println("Resuming Game (Pas de reset)");
            lastEscTime = System.currentTimeMillis(); // Reset du timer pause
            return; // ON ARRÊTE ICI
        }
        // ---------------------------

        // Si on arrive ici, c'est soit une NOUVELLE PARTIE, soit un NOUVEAU NIVEAU
        Level level = levels.get(currentLevel);

        enemies = new EnemyGroup();
        projectiles.clear();
        powerUps.clear(); // Pensez aussi à vider les powerups

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
        if (game.getInputHandler().isActionActive(GameConfig.Action.SHOOT)) {
            if (player.hasTripleShot()) {
                Projectile[] triple = player.shootTriple();
                if (triple != null) {
                    for (Projectile p : triple) {
                        projectiles.add(p);
                        SoundManager.getInstance().playSFX("shoot");
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
                    SoundManager.getInstance().playSFX("invaderkilled");
                    // 30% chance to drop power-up (adjust this value)
                    // 0.1 = 10%, 0.2 = 20%, 0.3 = 30%, 0.5 = 50%, etc.
                    if (Math.random() < 0.3) {
                        spawnPowerUp((int)p.getX(), (int)p.getY());
                    }
                }
            } else {
                if (!player.hasShield() && checkPlayerCollision(p)) {
                    p.setActive(false);
                    SoundManager.getInstance().playSFX("explosion");
                    game.changeState(new GameOverState(game, game.getScore()));
                    return;
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
                game.changeState(new WinState(game, game.getScore()));
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

        // Draw HUD with all info
        hud.drawGameHUD(gc, game.getScore(), currentLevel + 1,
                player.hasShield(), player.hasRapidFire(),
                player.hasTripleShot(), player.hasSpeedBoost());
    }

    @Override
    public void exit() { }
}