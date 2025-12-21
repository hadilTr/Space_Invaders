package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig;
import tn.client.space_invaders.services.SoundManager;
import tn.client.space_invaders.view.SpaceBackground;

public class GameOverState implements GameState {

    private Game game;
    private SpaceBackground background;
    private int finalScore;

    private String[] options = {"REJOUER", "MENU PRINCIPAL"};
    private int currentSelection = 0;
    private long lastInputTime;

    public GameOverState(Game game, int score) {
        this.game = game;
        this.finalScore = score;
        this.background = new SpaceBackground();
        this.lastInputTime = System.currentTimeMillis();
    }

    @Override
    public void enter() {
    }

    @Override
    public void update() {
        background.update();
        long now = System.currentTimeMillis();
        if (now - lastInputTime < 300) return;

        if (game.getInputHandler().isActionActive(GameConfig.Action.UP) ||
                game.getInputHandler().isActionActive(GameConfig.Action.DOWN)) {
            SoundManager.getInstance().playSFX("select");
            currentSelection = (currentSelection == 0) ? 1 : 0; // Bascule entre 0 et 1
            lastInputTime = now;
        }

        if (game.getInputHandler().isActionActive(GameConfig.Action.SELECT)) {
            if (currentSelection == 0) {
                SoundManager.getInstance().playSFX("select");
                game.resetScore();
                game.changeState(new PlayingState(game));
            } else {
                SoundManager.getInstance().playSFX("select");
                game.changeState(new MenuState(game));
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        background.draw(gc);

        gc.setTextAlign(TextAlignment.CENTER);

        gc.setFill(Color.RED);
        gc.setEffect(new javafx.scene.effect.Glow(0.8));
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        gc.fillText("GAME OVER", Game.WIDTH / 2, 200);
        gc.setEffect(null);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 30));
        gc.fillText("Score Final : " + finalScore, Game.WIDTH / 2, 280);

        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 30));
        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection) {
                gc.setFill(Color.YELLOW);
                gc.fillText("> " + options[i] + " <", Game.WIDTH / 2, 400 + i * 50);
            } else {
                gc.setFill(Color.GRAY);
                gc.fillText(options[i], Game.WIDTH / 2, 400 + i * 50);
            }
        }
    }

    @Override
    public void exit() {}
}