package tn.client.space_invaders.patterns.factory;

import tn.client.space_invaders.model.GameComponent;
import tn.client.space_invaders.patterns.composite.Enemy;
import tn.client.space_invaders.patterns.decorator.DoubleGunDecorator;
import tn.client.space_invaders.patterns.decorator.Player;
import tn.client.space_invaders.patterns.decorator.ShieldDecorator;

public class EntityFactory {

    public static GameComponent createPlayer(int x, int y, int width, int height, boolean withShield, boolean withDoubleGun) {
        GameComponent player = new Player(x, y, width, height);
        if (withShield) {
            player = new ShieldDecorator(player);
        }
        if (withDoubleGun) {
            player = new DoubleGunDecorator(player);
        }
        return player;
    }

    public static GameComponent createEnemy(int x, int y, int width, int height) {
        return new Enemy(x, y, width, height);
    }
}