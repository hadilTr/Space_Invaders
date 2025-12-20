package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig;
import tn.client.space_invaders.core.GameConfig.Action;
import tn.client.space_invaders.services.SoundManager;
import tn.client.space_invaders.view.SpaceBackground;

public class ControlsState implements GameState {

    private Game game;
    private GameState previousState;
    private SpaceBackground background;

    private Action[] actions = {Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT, Action.SHOOT};
    private String[] labels = {"HAUT", "BAS", "GAUCHE", "DROITE", "TIRER"};

    private int currentSelection = 0;
    private boolean isRebinding = false; // Est-on en train d'attendre une touche ?
    private long lastInputTime;

    public ControlsState(Game game, GameState previousState) {
        this.game = game;
        this.previousState = previousState;
        this.background = new SpaceBackground();
        this.lastInputTime = System.currentTimeMillis();
    }

    @Override
    public void enter() {
        this.lastInputTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        background.update();
        long now = System.currentTimeMillis();

        if (isRebinding) {
            KeyCode newKey = game.getInputHandler().consumeLastKey();
            if (newKey != null) {
                // On interdit ECHAP pour éviter les bugs
                if (newKey != KeyCode.ESCAPE) {
                    GameConfig.getInstance().setKey(actions[currentSelection], newKey);
                }
                isRebinding = false;
                lastInputTime = now;
            }
            return; // On ne fait rien d'autre tant qu'on attend la touche
        }

        if (now - lastInputTime < 200) return;

        // Retour en arrière (Bouton Options ou Echap)
        if (game.getInputHandler().isActionActive(Action.SELECT) && currentSelection == actions.length) {
            game.changeState(previousState);
            return;
        }
        if (game.getInputHandler().isActionActive(Action.PAUSE)) { // ECHAP
            game.changeState(previousState);
            return;
        }

        // Navigation Haut/Bas
        if (game.getInputHandler().isActionActive(Action.UP)) {
            currentSelection--;
            if (currentSelection < 0) currentSelection = actions.length; // +1 pour le bouton Retour
            SoundManager.getInstance().playSFX("select");
            lastInputTime = now;
        }
        if (game.getInputHandler().isActionActive(Action.DOWN)) {
            currentSelection++;
            if (currentSelection > actions.length) currentSelection = 0;
            SoundManager.getInstance().playSFX("select");
            lastInputTime = now;
        }

        // Sélectionner pour modifier
        if (game.getInputHandler().isActionActive(Action.SELECT)) {
            SoundManager.getInstance().playSFX("select");
            if (currentSelection < actions.length) {
                isRebinding = true;
                game.getInputHandler().consumeLastKey(); // Vider le buffer pour pas que "Entrée" soit pris comme nouvelle touche
                lastInputTime = now;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        background.draw(gc);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        gc.fillText("CONFIGURER TOUCHES", Game.WIDTH / 2, 80);

        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 25));

        for (int i = 0; i < actions.length; i++) {
            int y = 180 + i * 60;

            // Si c'est l'élément sélectionné
            if (i == currentSelection) {
                gc.setFill(isRebinding ? Color.RED : Color.YELLOW); // ROUGE si on attend une touche
                if (isRebinding) {
                    gc.fillText(labels[i] + " : ???", Game.WIDTH / 2, y);
                } else {
                    KeyCode k = GameConfig.getInstance().getKey(actions[i]);
                    gc.fillText("> " + labels[i] + " : " + k.getName() + " <", Game.WIDTH / 2, y);
                }
            } else {
                gc.setFill(Color.LIGHTGRAY);
                KeyCode k = GameConfig.getInstance().getKey(actions[i]);
                gc.fillText(labels[i] + " : " + k.getName(), Game.WIDTH / 2, y);
            }
        }

        // Bouton RETOUR (situé après la liste)
        int yRetour = 180 + actions.length * 60 + 20;
        if (currentSelection == actions.length) {
            gc.setFill(Color.YELLOW);
            gc.fillText("> RETOUR <", Game.WIDTH / 2, yRetour);
        } else {
            gc.setFill(Color.LIGHTGRAY);
            gc.fillText("RETOUR", Game.WIDTH / 2, yRetour);
        }

        // Aide
        gc.setFont(Font.font("Arial", 15));
        gc.setFill(Color.CYAN);
        if (isRebinding) {
            gc.fillText("Appuyez sur la nouvelle touche...", Game.WIDTH / 2, Game.HEIGHT - 50);
        } else {
            gc.fillText("Entrée pour modifier - Echap pour retour", Game.WIDTH / 2, Game.HEIGHT - 50);
        }
    }

    @Override
    public void exit() {}
}