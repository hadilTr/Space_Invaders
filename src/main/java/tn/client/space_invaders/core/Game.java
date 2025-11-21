package tn.client.space_invaders.core;

import tn.client.space_invaders.controller.InputHandler;
import tn.client.space_invaders.patterns.state.GameState;
import tn.client.space_invaders.patterns.state.MenuState;
import tn.client.space_invaders.view.GamePanel;
import tn.client.space_invaders.view.GameWindow;

import java.awt.*;


public class Game {
    private GameState currentState;
    private GameWindow window;
    private InputHandler inputHandler;
    private boolean running;
    private int score;

    // Game loop constants
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    public Game() {
        initialize();
    }

    private void initialize() {
        this.window = new GameWindow();
        this.inputHandler = new InputHandler();
        this.window.addKeyListener(inputHandler);

        // Start with menu state
        this.currentState = new MenuState(this);
        this.score = 0;
    }

    public void start() {
        running = true;
        gameLoop();
    }

    private void gameLoop() {
        long lastUpdateTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            long updateLength = now - lastUpdateTime;
            lastUpdateTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);

            // Update game logic
            update(delta);

            // Render game
            render();

            // FPS control
            try {
                long sleepTime = (lastUpdateTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void update(double delta) {
        currentState.update(delta);
    }

    private void render() {
        window.render(currentState);
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void exit() {
        running = false;
        window.dispose();
        System.exit(0);
    }
}