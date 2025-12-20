package tn.client.space_invaders.patterns.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig; // Assurez-vous d'avoir créé GameConfig
import tn.client.space_invaders.services.SoundManager;
import tn.client.space_invaders.view.SpaceBackground; // Votre classe de fond

public class MenuState implements GameState {

    private Game game;
    private SpaceBackground background;

    // Options du menu
    private String[] options = {"JOUER", "OPTIONS", "QUITTER"};
    private int currentSelection = 0;

    // Timer pour la navigation
    private long lastInputTime;

    public MenuState(Game game) {
        this.game = game;
        this.background = new SpaceBackground(); // On réutilise votre joli fond
    }

    @Override
    public void enter() {
        System.out.println("--- MENU PRINCIPAL ---");
        this.lastInputTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        background.update();


        long now = System.currentTimeMillis();
        if (now - lastInputTime < 300) return;

        // Navigation HAUT
        if (game.getInputHandler().isActionActive(GameConfig.Action.UP)) {
            currentSelection--;
            if (currentSelection < 0) currentSelection = options.length - 1;
            SoundManager.getInstance().playSFX("select");
            lastInputTime = now;
        }

        // Navigation BAS
        if (game.getInputHandler().isActionActive(GameConfig.Action.DOWN)) {
            currentSelection++;
            if (currentSelection >= options.length) currentSelection = 0;
            SoundManager.getInstance().playSFX("select");
            lastInputTime = now;
        }

        // Validation (ENTRÉE)
        if (game.getInputHandler().isActionActive(GameConfig.Action.SELECT)) {
            lastInputTime = now; // Anti-rebond
            SoundManager.getInstance().playSFX("select");
            selectOption();
        }
    }

    private void selectOption() {
        switch (currentSelection) {
            case 0: // JOUER
                game.changeState(new PlayingState(game));
                break;
            case 1: // OPTIONS
                game.changeState(new OptionsState(game, this)); // On passera 'this' pour revenir ici
                break;
            case 2: // QUITTER
                System.exit(0);
                break;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        // 1. Dessiner le fond étoilé
        background.draw(gc);

        // 2. Titre
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.CYAN);
        gc.setEffect(new javafx.scene.effect.Glow(0.8)); // Petit effet néon
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        gc.fillText("SPACE INVADERS", Game.WIDTH / 2, 150);
        gc.setEffect(null); // On retire l'effet pour la suite

        // 3. Dessiner les options
        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 30));

        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection) {
                // Option sélectionnée : JAUNE + Curseur ">"
                gc.setFill(Color.YELLOW);
                gc.fillText("> " + options[i] + " <", Game.WIDTH / 2, 300 + i * 50);
            } else {
                // Option normale : BLANC GRISÉ
                gc.setFill(Color.LIGHTGRAY);
                gc.fillText(options[i], Game.WIDTH / 2, 300 + i * 50);
            }
        }

        // Crédits bas de page
        gc.setFont(Font.font("Arial", 12));
        gc.setFill(Color.GRAY);
        gc.fillText("Projet Design Patterns 2025", Game.WIDTH / 2, Game.HEIGHT - 30);
    }

    @Override
    public void exit() {}
}