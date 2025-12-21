package tn.client.space_invaders.model;

import javafx.scene.canvas.GraphicsContext;

public interface GameComponent {
    void update();
    void draw(GraphicsContext gc);
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}