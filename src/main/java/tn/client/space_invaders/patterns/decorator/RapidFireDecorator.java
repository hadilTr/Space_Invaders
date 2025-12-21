package tn.client.space_invaders.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.model.Projectile;
import java.util.Collections;
import java.util.List;

public class RapidFireDecorator extends PlayerDecorator {

    public RapidFireDecorator(Player decoratedPlayer) {
        super(decoratedPlayer);
        this.duration = 5000; // 5 secondes
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.setFill(Color.YELLOW);
        gc.fillOval(getX() + width/2 - 2, getY() + height/2, 4, 4);
    }

    @Override
    public List<Projectile> shoot() {
        long now = System.currentTimeMillis();


        if (now - decoratedPlayer.lastShotTime > 150) {
            decoratedPlayer.lastShotTime = now;
            return decoratedPlayer.createProjectiles();
        }
        return Collections.emptyList();
    }
}