package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode; // Import KeyCode
import javafx.scene.paint.Color;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.model.Projectile; // Import Projectile
import tn.client.space_invaders.patterns.composite.EnemyGroup;
import tn.client.space_invaders.patterns.decorator.Player; // Attention : on doit caster en Player
import tn.client.space_invaders.patterns.factory.EntityFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayingState implements GameState {

    private Game game;
    private Player player; // On change le type de GameComponent à Player pour accéder à shoot()
    private EnemyGroup enemies;
    private long lastEscTime = 0;
    private List<Projectile> projectiles = new ArrayList<>();

    public PlayingState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        if (player == null) {
            System.out.println("Starting New Game");
            player = (Player) EntityFactory.createPlayer(game, 375, 500, 50, 50);
            enemies = new EnemyGroup();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 8; j++) {
                    enemies.add(EntityFactory.createEnemy(50 + j * 60, 50 + i * 40, 40, 30));
                }
            }
            projectiles.clear();
        } else {
            System.out.println("Resuming Game from Pause");
        }

        lastEscTime = System.currentTimeMillis();
    }

    @Override
    public void update() {

        if (game.getInputHandler().isKeyPressed(KeyCode.ESCAPE)) {
            long now = System.currentTimeMillis();
            if (now - lastEscTime > 200) {
                lastEscTime = now; // Mise à jour du temps
                game.changeState(new PauseState(game, this));
                return;
            }
        }


        // 1. Mouvements
        if (player != null) player.update();
        if (enemies != null) enemies.update();

        // 2. Gestion du TIR (Espace)
        if (game.getInputHandler().isKeyPressed(KeyCode.SPACE)) {
            Projectile p = player.shoot();
            if (p != null) {
                projectiles.add(p);
                // Optionnel : Jouer un son ici
            }
        }

        if (Math.random() < 0.005) {
            Projectile p = enemies.shootRandom();
            if (p != null) {
                projectiles.add(p);
            }
        }

        // --- MISE A JOUR PROJECTILES ---
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.update();

            if (p.isEnemyShot()) {
                // CAS 1 : C'est un tir ennemi -> Vérifier collision avec JOUEUR
                if (checkPlayerCollision(p)) {
                    p.setActive(false);
                    // GAME OVER !
                    System.out.println("PERDU !");
                    // game.changeState(new GameOverState(game)); // On le fera juste après
                    game.changeState(new MenuState(game)); // Retour au menu pour l'instant
                }
            } else {
                // CAS 2 : C'est un tir joueur -> Vérifier collision avec ENNEMIS
                if (enemies.checkCollision(p)) {
                    p.setActive(false);
                    game.addScore(100);
                }
            }

            if (!p.isActive()) {
                it.remove();
            }
        }

        // 4. Condition de victoire (Si plus d'ennemis)
        if (enemies.isEmpty()) {
            System.out.println("GAGNÉ !");
            // Plus tard : game.changeState(new WinState(game));
            // Pour l'instant, on relance le niveau :
            enter();
        }
    }

    private boolean checkPlayerCollision(Projectile p) {
        if (player == null) return false;

        // C'est ici que le DECORATOR (Bouclier) interviendra plus tard !
        // Pour l'instant, on teste juste les rectangles.
        return p.getX() < player.getX() + player.getWidth() &&
                p.getX() + p.getWidth() > player.getX() &&
                p.getY() < player.getY() + player.getHeight() &&
                p.getY() + p.getHeight() > player.getY();
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (player != null) player.draw(gc);
        if (enemies != null) enemies.draw(gc);

        // Dessiner les projectiles
        for (Projectile p : projectiles) {
            p.draw(gc);
        }

        // Afficher le Score
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + game.getScore(), 10, 20);
    }

    @Override
    public void exit() {
    }
}