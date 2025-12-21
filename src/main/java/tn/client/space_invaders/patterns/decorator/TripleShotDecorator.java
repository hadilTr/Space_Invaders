package tn.client.space_invaders.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.patterns.factory.EntityFactory;

import java.util.ArrayList;
import java.util.List;

public class TripleShotDecorator extends PlayerDecorator {

    public TripleShotDecorator(Player decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        // Canons violets
        gc.setFill(Color.PURPLE);
        gc.fillRect(getX() - 5, getY() + 10, 5, 10);
        gc.fillRect(getX() + getWidth(), getY() + 10, 5, 10);
    }

    @Override
    public List<Projectile> shoot() {
        List<Projectile> baseShots = decoratedPlayer.shoot();

        if (!baseShots.isEmpty()) {
            List<Projectile> combinedShots = new ArrayList<>(baseShots);
            addSideMissiles(combinedShots);
            return combinedShots;
        }
        return baseShots; // Liste vide
    }

    @Override
    public List<Projectile> createProjectiles() {
        List<Projectile> baseShots = decoratedPlayer.createProjectiles();
        List<Projectile> combinedShots = new ArrayList<>(baseShots);
        addSideMissiles(combinedShots);
        return combinedShots;
    }

    private void addSideMissiles(List<Projectile> shots) {
        shots.add(EntityFactory.createProjectile((int)(getX() - 5), (int)(getY() + 10)));
        shots.add(EntityFactory.createProjectile((int)(getX() + getWidth()), (int)(getY() + 10)));
    }
}