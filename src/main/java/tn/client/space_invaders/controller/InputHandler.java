package tn.client.space_invaders.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.patterns.state.MenuState;
import tn.client.space_invaders.patterns.state.PlayingState;

import java.util.HashSet;
import java.util.Set;

public class InputHandler {

    private Game game;
    private Set<KeyCode> activeKeys = new HashSet<>();

    public InputHandler(Game game) {
        this.game = game;
    }

    // On attache les écouteurs à la Scène JavaFX
    public void attachToScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            activeKeys.add(code);

            // Gestion spéciale pour le Menu (appuyer une fois sur Entrée)
            if (game.getCurrentState() instanceof MenuState) {
                if (code == KeyCode.ENTER) {
                    game.changeState(new PlayingState(game));
                }
            }

            // Consume the event to prevent it from propagating
            event.consume();
        });

        scene.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
            event.consume();
        });
    }

    public boolean isKeyPressed(KeyCode key) {
        return activeKeys.contains(key);
    }
}