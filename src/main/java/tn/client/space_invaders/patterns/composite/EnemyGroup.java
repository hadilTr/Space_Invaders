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

    // Paramètres de déplacement du groupe
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

        // 1. On déplace tout le monde horizontalement
        for (GameComponent child : children) {
            // On doit caster en GameObject pour modifier x et y
            if (child instanceof GameObject) {
                GameObject enemy = (GameObject) child;
                enemy.setX(enemy.getX() + (speed * direction));

                // Vérification des bords
                if (enemy.getX() <= 0 || enemy.getX() + enemy.getWidth() >= Game.WIDTH) {
                    hitWall = true;
                }
            }
        }

        // 2. Si un mur est touché, tout le groupe change de direction et descend
        if (hitWall) {
            direction *= -1; // Inverse la direction (Droite <-> Gauche)
            for (GameComponent child : children) {
                if (child instanceof GameObject) {
                    GameObject enemy = (GameObject) child;
                    enemy.setY(enemy.getY() + dropAmount); // Tout le monde descend
                    // On ajoute un petit correctif pour les décoller du mur immédiatement
                    enemy.setX(enemy.getX() + (speed * direction));
                }
            }
        }
    }

    public boolean checkCollision(Projectile p) {
        boolean hit = false;

        // On utilise un Iterator pour pouvoir supprimer un élément pendant qu'on parcourt la liste
        Iterator<GameComponent> iterator = children.iterator();
        while (iterator.hasNext()) {
            GameComponent child = iterator.next();

            if (child instanceof GameObject) {
                GameObject enemy = (GameObject) child;

                // TEST DE COLLISION SIMPLE (Rectangle vs Rectangle)
                if (p.getX() < enemy.getX() + enemy.getWidth() &&
                        p.getX() + p.getWidth() > enemy.getX() &&
                        p.getY() < enemy.getY() + enemy.getHeight() &&
                        p.getY() + p.getHeight() > enemy.getY()) {

                    // BOUM !
                    iterator.remove(); // L'ennemi meurt (on le retire de la liste)
                    hit = true;
                    break; // Un projectile ne tue qu'un seul ennemi
                }
            }
        }
        return hit;
    }

    public Projectile shootRandom() {
        if (children.isEmpty()) return null;

        // On prend un enfant au hasard
        int index = random.nextInt(children.size());
        GameComponent shooter = children.get(index);

        if (shooter instanceof GameObject) {
            GameObject s = (GameObject) shooter;
            // On crée un tir qui part du bas de cet ennemi
            return EntityFactory.createEnemyProjectile(s.getX() + s.getWidth()/2, s.getY() + s.getHeight());
        }
        return null;
    }

    // Petite méthode utilitaire pour savoir si on a gagné
    public boolean isEmpty() {
        return children.isEmpty();
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (GameComponent child : children) {
            child.draw(gc);
        }
    }

    // Méthodes de l'interface (non utilisées pour le groupe global mais requises)
    public int getX() { return 0; }
    public int getY() { return 0; }
    public int getWidth() { return 0; }
    public int getHeight() { return 0; }
}