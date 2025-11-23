package tn.client.space_invaders.patterns.composite;

import tn.client.space_invaders.model.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends GameObject {

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        // Mouvement de l'ennemi
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Classic Space Invader alien shape

        // Body - main rectangular body
        gc.setFill(Color.LIME);
        gc.fillRect(x + width * 0.2, y + height * 0.3, width * 0.6, height * 0.5);

        // Head - round top
        gc.fillOval(x + width * 0.25, y, width * 0.5, height * 0.4);

        // Eyes
        gc.setFill(Color.BLACK);
        gc.fillOval(x + width * 0.3, y + height * 0.15, width * 0.15, height * 0.2);
        gc.fillOval(x + width * 0.55, y + height * 0.15, width * 0.15, height * 0.2);

        // Antenna left
        gc.setFill(Color.LIME);
        gc.fillRect(x + width * 0.15, y - height * 0.15, width * 0.1, height * 0.2);
        gc.fillOval(x + width * 0.1, y - height * 0.2, width * 0.2, height * 0.15);

        // Antenna right
        gc.fillRect(x + width * 0.75, y - height * 0.15, width * 0.1, height * 0.2);
        gc.fillOval(x + width * 0.7, y - height * 0.2, width * 0.2, height * 0.15);

        // Arms/Tentacles
        gc.fillRect(x, y + height * 0.4, width * 0.2, height * 0.15);
        gc.fillRect(x + width * 0.8, y + height * 0.4, width * 0.2, height * 0.15);

        // Legs
        gc.fillRect(x + width * 0.25, y + height * 0.8, width * 0.15, height * 0.2);
        gc.fillRect(x + width * 0.6, y + height * 0.8, width * 0.15, height * 0.2);

        // Add some details - spots on body
        gc.setFill(Color.DARKGREEN);
        gc.fillOval(x + width * 0.3, y + height * 0.5, width * 0.1, height * 0.1);
        gc.fillOval(x + width * 0.6, y + height * 0.5, width * 0.1, height * 0.1);
    }
}