package tn.client.space_invaders.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.core.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceBackground {

    private List<Star> stars;
    private Random random;

    public SpaceBackground() {
        this.random = new Random();
        this.stars = new ArrayList<>();

        // Create 200 stars at random positions
        for (int i = 0; i < 200; i++) {
            stars.add(new Star(
                    random.nextInt(Game.WIDTH),
                    random.nextInt(Game.HEIGHT),
                    random.nextDouble() * 2 + 0.5,  // Size between 0.5 and 2.5
                    random.nextDouble() * 0.5 + 0.3  // Speed between 0.3 and 0.8
            ));
        }
    }

    public void update() {
        for (Star star : stars) {
            star.y += star.speed;

            // Reset star to top when it goes off screen
            if (star.y > Game.HEIGHT) {
                star.y = 0;
                star.x = random.nextInt(Game.WIDTH);
            }
        }
    }

    public void draw(GraphicsContext gc) {
        // Draw dark space gradient background
        for (int i = 0; i < Game.HEIGHT; i++) {
            double ratio = (double) i / Game.HEIGHT;
            int red = (int)(0 * (1 - ratio) + 10 * ratio);
            int green = (int)(0 * (1 - ratio) + 5 * ratio);
            int blue = (int)(20 * (1 - ratio) + 30 * ratio);

            gc.setStroke(Color.rgb(red, green, blue));
            gc.strokeLine(0, i, Game.WIDTH, i);
        }

        // Draw stars with varying brightness
        for (Star star : stars) {
            double brightness = 0.5 + Math.sin(System.currentTimeMillis() * 0.001 + star.x) * 0.5;
            int colorValue = (int)(brightness * 255);

            gc.setFill(Color.rgb(colorValue, colorValue, colorValue));
            gc.fillOval(star.x, star.y, star.size, star.size);

            // Add glow effect for bigger stars
            if (star.size > 1.5) {
                gc.setFill(Color.rgb(colorValue, colorValue, colorValue, 0.3));
                gc.fillOval(star.x - 1, star.y - 1, star.size + 2, star.size + 2);
            }
        }

        // Add some colorful nebula effects
        gc.setFill(Color.rgb(100, 50, 150, 0.03));
        gc.fillOval(50, 100, 300, 200);

        gc.setFill(Color.rgb(50, 100, 200, 0.03));
        gc.fillOval(500, 300, 250, 180);

        gc.setFill(Color.rgb(150, 50, 100, 0.02));
        gc.fillOval(200, 400, 200, 150);
    }

    private static class Star {
        double x, y, size, speed;

        Star(double x, double y, double size, double speed) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
        }
    }
}