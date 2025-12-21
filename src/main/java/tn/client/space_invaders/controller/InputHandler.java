package tn.client.space_invaders.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig; // Import Config

import java.util.HashSet;
import java.util.Set;

public class InputHandler {

    private Game game;
    private Set<KeyCode> activeKeys = new HashSet<>();
    private KeyCode lastKey = null;

    public InputHandler(Game game) {
        this.game = game;
    }

    public void attachToScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
                    activeKeys.add(event.getCode());
                    lastKey = event.getCode();
        });
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));
    }

    public boolean isKeyPressed(KeyCode key) {
        return activeKeys.contains(key);
    }

    public boolean isActionActive(GameConfig.Action action) {
        KeyCode boundKey = GameConfig.getInstance().getKey(action);
        return activeKeys.contains(boundKey);
    }

    public KeyCode consumeLastKey() {
        KeyCode k = lastKey;
        lastKey = null; // On "consomme" l'événement pour ne pas le lire 2 fois
        return k;
    }
}