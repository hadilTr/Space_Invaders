package tn.client.space_invaders.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import tn.client.space_invaders.core.Game; // Import Game
import tn.client.space_invaders.model.GameObject;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.patterns.factory.EntityFactory;

public class Player extends GameObject {

    private Game game;
    private int speed = 5;
    private long lastShotTime = 0;
    private long shootCooldown = 500;

    // On ajoute 'Game game' au constructeur
    public Player(Game game, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.game = game;
    }

    @Override
    public void update() {
        // 1. Vérifier la flèche GAUCHE
        if (game.getInputHandler().isKeyPressed(KeyCode.LEFT)) {
            x -= speed;
        }

        // 2. Vérifier la flèche DROITE
        if (game.getInputHandler().isKeyPressed(KeyCode.RIGHT)) {
            x += speed;
        }

        // 3. Empêcher de sortir de l'écran (Boundaries)
        if (x < 0) x = 0;
        if (x > Game.WIDTH - width) x = Game.WIDTH - width;
    }

    public Projectile shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime > shootCooldown) {
            lastShotTime = now;
            // On crée le projectile au centre du vaisseau
            return EntityFactory.createProjectile(x + width / 2 - 2, y);
        }
        return null; // Pas de tir si cooldown pas fini
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, width, height);
    }
}