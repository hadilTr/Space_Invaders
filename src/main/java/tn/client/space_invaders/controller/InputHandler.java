package tn.client.space_invaders.controller;

import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.patterns.state.MenuState;
import tn.client.space_invaders.patterns.state.PlayingState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.getCurrentState() instanceof MenuState) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                game.changeState(new PlayingState(game));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}