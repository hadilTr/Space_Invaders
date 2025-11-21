package tn.client.space_invaders.patterns.state;

import java.awt.*;

public interface GameState {
    void enter();
    void update();
    void draw(Graphics g);
    void exit();
}