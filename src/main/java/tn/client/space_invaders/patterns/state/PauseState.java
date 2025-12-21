package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig;
import tn.client.space_invaders.services.SoundManager;

public class PauseState implements GameState {

    private Game game;
    private GameState returnState;
    private String[] options = {"REPRENDRE", "OPTIONS", "QUITTER"};
    private int currentSelection = 0;
    private long lastInputTime = 0;

    public PauseState(Game game, GameState returnState) {
        this.game = game;
        this.returnState = returnState;
        this.lastInputTime = System.currentTimeMillis();
    }

    @Override
    public void enter() {
        this.lastInputTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastInputTime < 300) return;

        if (game.getInputHandler().isActionActive(GameConfig.Action.PAUSE)) {
            game.changeState(returnState);
            return;
        }

        if (game.getInputHandler().isActionActive(GameConfig.Action.UP)) {
            currentSelection--;
            if (currentSelection < 0) currentSelection = options.length - 1;
            SoundManager.getInstance().playSFX("select");
            lastInputTime = now;
        }
        if (game.getInputHandler().isActionActive(GameConfig.Action.DOWN)) {
            currentSelection++;
            if (currentSelection >= options.length) currentSelection = 0;
            SoundManager.getInstance().playSFX("select");
            lastInputTime = now;
        }
        if (game.getInputHandler().isActionActive(GameConfig.Action.SELECT)) {
            lastInputTime = now;
            SoundManager.getInstance().playSFX("select");
            executeChoice();
        }
    }

    private void executeChoice() {
        switch (currentSelection) {
            case 0: // REPRENDRE
                game.changeState(returnState);
                break;
            case 1: // OPTIONS
                game.changeState(new OptionsState(game, this)); // On reviendra au menu Pause
                break;
            case 2: // QUITTER
                game.changeState(new MenuState(game)); // Retour au menu principal
                break;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        returnState.draw(gc);

        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gc.fillText("PAUSE", Game.WIDTH / 2, 200);

        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 30));
        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection) {
                gc.setFill(Color.YELLOW);
                gc.fillText("> " + options[i] + " <", Game.WIDTH / 2, 300 + i * 50);
            } else {
                gc.setFill(Color.GRAY);
                gc.fillText(options[i], Game.WIDTH / 2, 300 + i * 50);
            }
        }
    }

    @Override
    public void exit() {}
}