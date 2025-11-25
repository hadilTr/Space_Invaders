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

public class OptionsState implements GameState {

    private Game game;
    private GameState previousState; // Pour savoir où revenir (Menu ou Pause)
    private SpaceBackground background;

    private String[] options = {"VOLUME MUSIQUE", "VOLUME EFFETS","CONTROLES", "RETOUR"};
    private int currentSelection = 0;
    private long lastInputTime ;

    public OptionsState(Game game, GameState previousState) {
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
        if (now - lastInputTime < 300) return;

        // HAUT / BAS (Changer de ligne)
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

        // GAUCHE / DROITE (Changer les valeurs)
        if (game.getInputHandler().isActionActive(GameConfig.Action.LEFT)) {
            adjustSetting(-0.1); // -10%
            lastInputTime = now;
        }
        if (game.getInputHandler().isActionActive(GameConfig.Action.RIGHT)) {
            adjustSetting(0.1); // +10%
            lastInputTime = now;
        }

        if (game.getInputHandler().isActionActive(GameConfig.Action.SELECT)) {
            SoundManager.getInstance().playSFX("select");
            if (currentSelection == 2) { // CONTROLES
                game.changeState(new ControlsState(game, this));
            }
            else if (currentSelection == 3) { // RETOUR
                game.changeState(previousState);
            }
            lastInputTime = now;
        }

    }

    private void adjustSetting(double amount) {
        GameConfig config = GameConfig.getInstance();
        if (currentSelection == 0) {
            config.setMusicVolume(config.getMusicVolume() + amount);
            SoundManager.getInstance().updateMusicVolume();
        } else if (currentSelection == 1) {
            config.setSfxVolume(config.getSfxVolume() + amount);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        background.draw(gc);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        gc.fillText("OPTIONS", Game.WIDTH / 2, 100);

        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 25));

        for (int i = 0; i < options.length; i++) {
            int y = 250 + i * 60;

            if (i == currentSelection) gc.setFill(Color.YELLOW);
            else gc.setFill(Color.LIGHTGRAY);

            // Affichage du nom de l'option
            gc.fillText(options[i], Game.WIDTH / 2, y);

            // Affichage des Barres de volume (seulement pour 0 et 1)
            if (i == 0 || i == 1) {
                drawVolumeBar(gc, i == 0 ? GameConfig.getInstance().getMusicVolume() : GameConfig.getInstance().getSfxVolume(), y + 20);
            }
        }
    }

    private void drawVolumeBar(GraphicsContext gc, double volume, int y) {
        int barWidth = 200;
        int barHeight = 10;
        int x = (Game.WIDTH - barWidth) / 2;

        // Contour
        gc.setStroke(Color.WHITE);
        gc.strokeRect(x, y, barWidth, barHeight);

        // Remplissage
        gc.setFill(Color.LIMEGREEN);
        gc.fillRect(x, y, barWidth * volume, barHeight);
    }

    @Override
    public void exit() {}
}