package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;

public interface GameState {
    void enter();
    void update();
    void draw(GraphicsContext gc);
    void exit();
}