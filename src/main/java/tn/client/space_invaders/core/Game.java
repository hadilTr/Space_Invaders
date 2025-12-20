package tn.client.space_invaders.core;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import tn.client.space_invaders.controller.InputHandler;
import tn.client.space_invaders.model.Level;
import tn.client.space_invaders.patterns.state.GameState;
import tn.client.space_invaders.patterns.state.MenuState;
import tn.client.space_invaders.view.SpaceBackground;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GameState currentState;
    private InputHandler inputHandler;
    private AnimationTimer gameLoop;
    private boolean isRunning = false;
    private int score;
    private List<Level> levels;
    private SpaceBackground spaceBackground;

    public Game() {
        this.score = 0;
        this.inputHandler = new InputHandler(this);
        this.spaceBackground = new SpaceBackground();
        this.currentState = new MenuState(this);
        this.currentState.enter();
    }

    public void start(GraphicsContext gc) {

        if (isRunning) {
            return;
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        };
        gameLoop.start();
        isRunning = true;
    }

    public void stop() {
        if (isRunning && gameLoop != null) {
            gameLoop.stop();
            isRunning = false;
        }
    }

    private void update() {
        // Update space background
        spaceBackground.update();

        if (currentState != null) {
            currentState.update();
        }
    }

    private void render(GraphicsContext gc) {
        gc.setEffect(null);
        gc.setGlobalAlpha(1.0);
        // Draw animated space background instead of black
        spaceBackground.draw(gc);

        // Draw current game state on top
        if (currentState != null) {
            currentState.draw(gc);
        }
    }

    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        this.currentState = newState;
        this.currentState.enter();
    }

    public List<Level> initializeLevels() {
        levels = new ArrayList<>();
        levels.add(new Level(1, 3, 5, 50, 50, 60, 40, 1, 0.005, 100));
        levels.add(new Level(2, 5, 8, 45, 40, 55, 35, 2, 0.008, 150));
        levels.add(new Level(3, 6, 10, 40, 60, 60, 45, 3, 0.01, 200));
        return levels;
    }

    public void resetScore() { this.score = 0; }

    public GameState getCurrentState() { return currentState; }
    public InputHandler getInputHandler() { return inputHandler; }
    public int getScore() { return score; }
    public void addScore(int points) { this.score += points; }
}