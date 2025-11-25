package tn.client.space_invaders.core;

import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;

public class GameConfig {

    private static GameConfig instance;

    // Volumes (0.0 à 1.0)
    private double musicVolume = 0.5;
    private double sfxVolume = 0.8;

    // Mapping des touches (Action -> Touche Clavier)
    // On utilise un Enum pour définir les actions possibles
    public enum Action {
        UP, DOWN, LEFT, RIGHT, SHOOT, PAUSE, SELECT
    }

    private Map<Action, KeyCode> keyBindings;

    private GameConfig() {
        keyBindings = new HashMap<>();
        // Touches par défaut
        keyBindings.put(Action.UP, KeyCode.UP);
        keyBindings.put(Action.DOWN, KeyCode.DOWN);
        keyBindings.put(Action.LEFT, KeyCode.LEFT);
        keyBindings.put(Action.RIGHT, KeyCode.RIGHT);
        keyBindings.put(Action.SHOOT, KeyCode.SPACE);
        keyBindings.put(Action.PAUSE, KeyCode.ESCAPE);
        keyBindings.put(Action.SELECT, KeyCode.ENTER);
    }

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    // Getters / Setters
    public double getMusicVolume() { return musicVolume; }
    public void setMusicVolume(double v) {
        this.musicVolume = Math.max(0, Math.min(1, v)); // Borner entre 0 et 1
    }

    public double getSfxVolume() { return sfxVolume; }
    public void setSfxVolume(double v) {
        this.sfxVolume = Math.max(0, Math.min(1, v));
    }

    public KeyCode getKey(Action action) {
        return keyBindings.get(action);
    }

    public void setKey(Action action, KeyCode key) {
        keyBindings.put(action, key);
    }
}