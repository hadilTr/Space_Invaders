package tn.client.space_invaders.model;

import java.awt.*;

public interface GameComponent {
    void update();
    void draw(Graphics g);
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}
