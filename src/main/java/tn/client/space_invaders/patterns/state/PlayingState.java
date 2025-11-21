package tn.client.space_invaders.patterns.state;

import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.model.GameComponent;
import tn.client.space_invaders.patterns.composite.EnemyGroup;
import tn.client.space_invaders.patterns.factory.EntityFactory;

import java.awt.*;

public class PlayingState implements GameState {

    private Game game;
    private GameComponent player;
    private EnemyGroup enemies;

    public PlayingState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println("Entering Playing State");
        // Crée un joueur avec un bouclier et un double canon
        player = EntityFactory.createPlayer(375, 500, 50, 50, true, true);

        // Crée un groupe d'ennemis
        enemies = new EnemyGroup();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                enemies.add(EntityFactory.createEnemy(100 + j * 60, 50 + i * 40, 40, 30));
            }
        }
    }

    @Override
    public void update() {
        player.update();
        enemies.update();
    }

    @Override
    public void draw(Graphics g) {
        player.draw(g);
        enemies.draw(g);
    }

    @Override
    public void exit() {
        System.out.println("Exiting Playing State");
    }
}
