package tn.client.space_invaders.patterns.state;

import tn.client.space_invaders.core.Game;

import java.awt.*;

public class MenuState implements GameState {

    private Game game;

    public MenuState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println("Entering Menu State");
    }

    @Override
    public void update() {
        // Logique du menu (ex: gestion des clics sur les boutons)
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("SPACE INVADERS", 150, 100);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Press ENTER to Play", 250, 300);
    }

    @Override
    public void exit() {
        System.out.println("Exiting Menu State");
    }
}