package tn.client.space_invaders.patterns.factory;

import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.model.GameComponent;
import tn.client.space_invaders.model.Projectile;
import tn.client.space_invaders.patterns.decorator.Player;
import tn.client.space_invaders.patterns.composite.Enemy;

public class EntityFactory {

    public static GameComponent createPlayer(Game game, int x, int y, int w, int h) {
        return new Player(game, x, y, w, h);
    }

    public static GameComponent createEnemy(int x, int y, int w, int h) {
        return new Enemy(x, y, w, h);
    }

    public static Projectile createProjectile(int x, int y) {
        return new Projectile(x, y, -10, false);
    }

    public static Projectile createEnemyProjectile(int x, int y) {
        return new Projectile(x, y, 5, true);
    }
}