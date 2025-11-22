package tn.client.space_invaders.core;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.controller.InputHandler;
import tn.client.space_invaders.patterns.state.GameState;
import tn.client.space_invaders.patterns.state.MenuState;

public class Game {

    // Constantes accessibles partout
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GameState currentState;
    private InputHandler inputHandler;
    private AnimationTimer gameLoop;
    private int score;

    public Game() {
        // Initialisation
        this.score = 0;
        this.inputHandler = new InputHandler(this);
        // On démarre sur le Menu
        this.currentState = new MenuState(this);
        this.currentState.enter();
    }

    public void start(GraphicsContext gc) {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // 1. Mise à jour logique
                update();
                // 2. Dessin
                render(gc);
            }
        };
        gameLoop.start();
    }

    private void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    private void render(GraphicsContext gc) {
        // Effacer l'écran (Fond Noir)
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Dessiner l'état actuel
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

    // Getters
    public GameState getCurrentState() { return currentState; }
    public InputHandler getInputHandler() { return inputHandler; }
    public int getScore() { return score; }
    public void addScore(int points) { this.score += points; }
}