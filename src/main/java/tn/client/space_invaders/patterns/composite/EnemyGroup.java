package tn.client.space_invaders.patterns.composite;

import tn.client.space_invaders.model.GameComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyGroup implements GameComponent {

    private List<GameComponent> enemies = new ArrayList<>();

    public void add(GameComponent enemy) {
        enemies.add(enemy);
    }

    public void remove(GameComponent enemy) {
        enemies.remove(enemy);
    }

    @Override
    public void update() {
        for (GameComponent enemy : enemies) {
            enemy.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (GameComponent enemy : enemies) {
            enemy.draw(g);
        }
    }

    @Override
    public int getX() {
        return 0; // Not applicable for a group
    }

    @Override
    public int getY() {
        return 0; // Not applicable for a group
    }

    @Override
    public int getWidth() {
        return 0; // Not applicable for a group
    }

    @Override
    public int getHeight() {
        return 0; // Not applicable for a group
    }
}