package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tn.client.space_invaders.core.Game;

public class PauseState implements GameState {

    private Game game;
    private GameState returnState;

    // Le moment précis où on est entré en pause
    private long startTime;

    public PauseState(Game game, GameState previousState) {
        this.game = game;
        this.returnState = previousState;
    }

    @Override
    public void enter() {
        System.out.println("Game Paused");
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (game.getInputHandler().isKeyPressed(KeyCode.ESCAPE)) {
            long now = System.currentTimeMillis();
            if (now - startTime > 200) {
                game.changeState(returnState);
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        returnState.draw(gc);

        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gc.fillText("PAUSE", 300, 300);

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gc.fillText("Press ESC to Resume", 290, 350);
    }

    @Override
    public void exit() {
        System.out.println("Resuming Game");
    }
}