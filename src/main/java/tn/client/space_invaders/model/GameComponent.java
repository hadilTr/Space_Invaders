package tn.client.space_invaders.model;

import javafx.scene.canvas.GraphicsContext; // <--- Changement majeur

public interface GameComponent {
    void update();
    void draw(GraphicsContext gc); // <--- On dessine avec le "Pinceau" JavaFX
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}