package tn.client.space_invaders.patterns.composite;

import javafx.scene.canvas.GraphicsContext;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.model.GameComponent;
import tn.client.space_invaders.model.GameObject;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.patterns.factory.EntityFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EnemyGroup implements GameComponent {

    private List<GameComponent> children = new ArrayList<>();

    private int direction = 1;
    private int speed = 1;
    private int dropAmount = 10;
    private Random random = new Random();

    public void add(GameComponent component) {
        children.add(component);
    }

    public void remove(GameComponent component) {
        children.remove(component);
    }

    @Override
    public void update() {
        boolean hitWall = false;

        for (GameComponent child : children) {
            if (child instanceof GameObject) {
                GameObject enemy = (GameObject) child;
                enemy.setX(enemy.getX() + (speed * direction));

                if (enemy.getX() <= 0 || enemy.getX() + enemy.getWidth() >= Game.WIDTH) {
                    hitWall = true;
                }
            }
        }

        if (hitWall) {
            direction *= -1;
            for (GameComponent child : children) {
                if (child instanceof GameObject) {
                    GameObject enemy = (GameObject) child;
                    enemy.setY(enemy.getY() + dropAmount); // Tout le monde descend
                    enemy.setX(enemy.getX() + (speed * direction));
                }
            }
        }
    }

    public boolean checkCollision(Projectile p) {
        boolean hit = false;

        Iterator<GameComponent> iterator = children.iterator();
        while (iterator.hasNext()) {
            GameComponent child = iterator.next();

            if (child instanceof GameObject) {
                GameObject enemy = (GameObject) child;

                if (p.getX() < enemy.getX() + enemy.getWidth() &&
                        p.getX() + p.getWidth() > enemy.getX() &&
                        p.getY() < enemy.getY() + enemy.getHeight() &&
                        p.getY() + p.getHeight() > enemy.getY()) {

                    iterator.remove();
                    hit = true;
                    break;
                }
            }
        }
        return hit;
    }

    public Projectile shootRandom() {
        if (children.isEmpty()) return null;

        int index = random.nextInt(children.size());
        GameComponent shooter = children.get(index);

        if (shooter instanceof GameObject) {
            GameObject s = (GameObject) shooter;
            return EntityFactory.createEnemyProjectile(s.getX() + s.getWidth()/2, s.getY() + s.getHeight());
        }
        return null;
    }

    public boolean isEmpty() {
        return children.isEmpty();
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (GameComponent child : children) {
            child.draw(gc);
        }
    }

    public int getX() { return 0; }
    public int getY() { return 0; }
    public int getWidth() { return 0; }
    public int getHeight() { return 0; }

    public void setSpeed(int speed) {
        this.speed=speed;
    }
}