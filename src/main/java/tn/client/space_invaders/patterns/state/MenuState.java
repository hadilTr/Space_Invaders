package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tn.client.space_invaders.core.Game;

public class MenuState implements GameState {

    private Game game;

    public MenuState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println("Entering Menu State");
    }

    @Override
    public void update() {
        // Logique menu
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Titre
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gc.fillText("SPACE INVADERS", 180, 200);

        // Instruction
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gc.fillText("Press ENTER to Play", 300, 350);
    }

    @Override
    public void exit() {
        System.out.println("Exiting Menu State");
    }
}