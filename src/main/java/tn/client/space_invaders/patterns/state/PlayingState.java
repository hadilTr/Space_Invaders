package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig;
import tn.client.space_invaders.model.Level;
import tn.client.space_invaders.model.PowerUp;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.patterns.composite.EnemyGroup;
import tn.client.space_invaders.patterns.decorator.*; // Import de tous les décorateurs
import tn.client.space_invaders.patterns.factory.EntityFactory;
import tn.client.space_invaders.services.GameLogger;
import tn.client.space_invaders.services.SoundManager;
import tn.client.space_invaders.view.HUD;

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
        if (levels == null) levels = game.initializeLevels();

        if (player != null && enemies != null && !enemies.isEmpty()) {
            GameLogger.getInstance().info("Reprise du jeu (Resume)");
            lastEscTime = System.currentTimeMillis();
            return;
        }

        Level level = levels.get(currentLevel);
        enemies = new EnemyGroup();
        projectiles.clear();
        powerUps.clear();

        if (player == null) {
            GameLogger.getInstance().info("Démarrage niveau " + (currentLevel + 1));
            player = (Player) EntityFactory.createPlayer(game, 375, 500, 50, 50);
        } else {

            GameLogger.getInstance().info("Chargement niveau " + (currentLevel + 1));
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

        // Gestion Pause
        if (game.getInputHandler().isKeyPressed(KeyCode.ESCAPE)) {
            long now = System.currentTimeMillis();
            if (now - lastEscTime > 200) {
                lastEscTime = now;
                game.changeState(new PauseState(game, this));
                return;
            }
        }

        if (player != null) {
            player.update();
            player = player.processExpiration();
        }
        if (enemies != null) enemies.update();

        if (game.getInputHandler().isActionActive(GameConfig.Action.SHOOT)) {

            List<Projectile> shots = player.shoot();
            if (!shots.isEmpty()) {
                projectiles.addAll(shots);
                SoundManager.getInstance().playSFX("shoot");
            }
        }

        Level level = levels.get(currentLevel);
        if (Math.random() < level.enemyFireRate) {
            Projectile p = enemies.shootRandom();
            if (p != null) projectiles.add(p);
        }

        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.update();

            if (!p.isEnemyShot()) {
                if (enemies.checkCollision(p)) {
                    p.setActive(false);
                    game.addScore(level.scorePerEnemy);
                    SoundManager.getInstance().playSFX("invaderkilled");
                    if (Math.random() < 0.3) { // 30% de chance de drop
                        spawnPowerUp((int)p.getX(), (int)p.getY());
                    }
                }
            } else {
                if (checkPlayerCollision(p)) {
                    p.setActive(false);

                    boolean isDead = player.takeHit();

                    if (isDead) {
                        SoundManager.getInstance().playSFX("explosion");
                        GameLogger.getInstance().error("Joueur détruit !");
                        game.changeState(new GameOverState(game, game.getScore()));
                        return;
                    } else {
                        GameLogger.getInstance().info("Bouclier a absorbé le tir !");
                    }
                }
            }
            if (!p.isActive()) it.remove();
        }

        Iterator<PowerUp> powerUpIt = powerUps.iterator();
        while (powerUpIt.hasNext()) {
            PowerUp powerUp = powerUpIt.next();
            powerUp.update();

            if (powerUp.checkCollision(player)) {
                activatePowerUp(powerUp.getType());
                powerUp.setActive(false);
                SoundManager.getInstance().playSFX("powerup"); // Si vous avez le son
            }
            if (!powerUp.isActive()) powerUpIt.remove();
        }

        if (enemies.isEmpty()) {
            currentLevel++;
            if (currentLevel >= levels.size()) {
                game.changeState(new WinState(game, game.getScore()));
            } else {
                loadNextLevel = true;
            }
        }
    }

    private void activatePowerUp(PowerUp.PowerUpType type) {
        GameLogger.getInstance().info("PowerUp contact : " + type);

        switch (type) {
            case SHIELD:
                if (!player.hasPowerUp(ShieldDecorator.class)) {
                    GameLogger.getInstance().info("PowerUp ACTIVÉ : Shield (8s)");
                    player = new ShieldDecorator(player);
                } else {
                    GameLogger.getInstance().info("PowerUp RECHARGÉ : Shield");
                    player.refreshPowerUp(ShieldDecorator.class);
                }
                break;

            case RAPID_FIRE:
                if (!player.hasPowerUp(RapidFireDecorator.class)) {
                    GameLogger.getInstance().info("PowerUp ACTIVÉ : Rapid Fire (8s)");
                    player = new RapidFireDecorator(player);
                } else {
                    GameLogger.getInstance().info("PowerUp RECHARGÉ : Rapid Fire");
                    player.refreshPowerUp(RapidFireDecorator.class);
                }
                break;

            case SPEED_BOOST:
                if (!player.hasPowerUp(SpeedBoostDecorator.class)) {
                    GameLogger.getInstance().info("PowerUp ACTIVÉ : Speed Boost (8s)");
                    player = new SpeedBoostDecorator(player);
                } else {
                    GameLogger.getInstance().info("PowerUp RECHARGÉ : Speed Boost");
                    player.refreshPowerUp(SpeedBoostDecorator.class);
                }
                break;

            case TRIPLE_SHOT:
                if (!player.hasPowerUp(TripleShotDecorator.class)) {
                    GameLogger.getInstance().info("PowerUp ACTIVÉ : Triple Shot (8s)");
                    player = new TripleShotDecorator(player);
                } else {
                    GameLogger.getInstance().info("PowerUp RECHARGÉ : Triple Shot");
                    player.refreshPowerUp(TripleShotDecorator.class);
                }
                break;
        }
    }

    private void spawnPowerUp(int x, int y) {
        PowerUp.PowerUpType[] types = PowerUp.PowerUpType.values();
        PowerUp.PowerUpType randomType = types[(int)(Math.random() * types.length)];
        powerUps.add(new PowerUp(x, y, randomType));
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

        for (Projectile p : projectiles) p.draw(gc);
        for (PowerUp pu : powerUps) pu.draw(gc);

        hud.drawGameHUD(gc, game.getScore(), currentLevel + 1,
                player.hasPowerUp(ShieldDecorator.class),
                player.hasPowerUp(RapidFireDecorator.class),
                player.hasPowerUp(TripleShotDecorator.class),
                player.hasPowerUp(SpeedBoostDecorator.class));
    }

    @Override
    public void exit() { }
}