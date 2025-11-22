package tn.client.space_invaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile extends GameObject {

    private int dy; // Vitesse verticale (négatif = monte, positif = descend)
    private boolean active = true;
    private boolean enemyShot; // Pour savoir si c'est un tir ennemi (pour la couleur/logique)

    public Projectile(int x, int y, int dy, boolean isEnemy) {
        super(x, y, 5, 10);
        this.dy = dy;
        this.enemyShot = isEnemy;
    }

    @Override
    public void update() {
        y += dy; // On ajoute la vitesse (si dy est négatif, ça monte)

        // Désactivation si hors écran
        if (y < 0 || y > 600) { // 600 = Hauteur écran
            active = false;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Jaune pour le joueur, Rouge pour l'ennemi
        gc.setFill(enemyShot ? Color.RED : Color.YELLOW);
        gc.fillRect(x, y, width, height);
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public boolean isEnemyShot() { return enemyShot; }
}