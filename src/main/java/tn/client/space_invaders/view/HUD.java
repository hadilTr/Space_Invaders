package tn.client.space_invaders.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment; // Important pour centrer le texte dynamique
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.core.GameConfig; // Import de la config

public class HUD {

    private Font titleFont;
    private Font regularFont;
    private Font smallFont;

    public HUD() {
        titleFont = Font.font("Arial", FontWeight.BOLD, 32);
        regularFont = Font.font("Arial", FontWeight.BOLD, 20);
        smallFont = Font.font("Arial", FontWeight.NORMAL, 14);
    }

    // --- NOUVELLE MÉTHODE UTILITAIRE ---
    // Récupère le nom de la touche (ex: "Space", "Enter", "A", "Left")
    private String getKeyName(GameConfig.Action action) {
        KeyCode key = GameConfig.getInstance().getKey(action);
        switch (key) {
            case LEFT: return "←";
            case RIGHT: return "→";
            case UP: return "↑";
            case DOWN: return "↓";
            case ENTER: return "ENTER"; // Plus joli que "ENTER" parfois
            case SPACE: return "SPACE";
            case ESCAPE: return "ESC";
            default: return key.getName().toUpperCase();
        }
    }

    public void drawGameHUD(GraphicsContext gc, int score, int level,
                            boolean hasShield, boolean hasRapidFire,
                            boolean hasTripleShot, boolean hasSpeedBoost) {

        // Draw semi-transparent panel at top
        gc.setFill(Color.rgb(0, 0, 0, 0.6));
        gc.fillRect(0, 0, Game.WIDTH, 70);

        // Draw score with glow effect
        gc.setFont(regularFont);
        gc.setTextAlign(TextAlignment.LEFT); // Reset alignement
        drawTextWithGlow(gc, "SCORE", 50, 25, Color.CYAN);
        gc.setFont(titleFont);
        drawTextWithGlow(gc, String.format("%06d", score), 60, 55, Color.WHITE);

        gc.setTextAlign(TextAlignment.CENTER);

        // 2. Le mot "LEVEL" (en haut)
        gc.setFont(regularFont);
        drawTextWithGlow(gc, "LEVEL", Game.WIDTH / 2, 25, Color.LIME);

        // 3. Le Numéro (en bas)
        gc.setFont(titleFont);
        drawTextWithGlow(gc, String.valueOf(level), Game.WIDTH / 2, 55, Color.WHITE);

        // --- IMPORTANT : RESET ALIGNEMENT ---
        // On remet l'alignement à GAUCHE pour que les Power-Ups ne soient pas décalés
        gc.setTextAlign(TextAlignment.LEFT);

        // Draw active power-ups on the right
        int powerUpX = Game.WIDTH - 220;
        int powerUpY = 15;

        gc.setFont(smallFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("ACTIVE POWER-UPS:", powerUpX +60, powerUpY);

        powerUpY += 20;

        if (hasShield) {
            drawPowerUpIcon(gc, powerUpX, powerUpY, Color.CYAN, "SHIELD");
            powerUpY += 25;
        }
        if (hasRapidFire) {
            drawPowerUpIcon(gc, powerUpX, powerUpY, Color.RED, "RAPID FIRE");
            powerUpY += 25;
        }
        if (hasTripleShot) {
            drawPowerUpIcon(gc, powerUpX, powerUpY, Color.PURPLE, "TRIPLE SHOT");
            powerUpY += 25;
        }
        if (hasSpeedBoost) {
            drawPowerUpIcon(gc, powerUpX, powerUpY, Color.YELLOW, "SPEED BOOST");
        }

        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, Game.HEIGHT - 30, Game.WIDTH, 30);

        gc.setFont(smallFont);
        gc.setFill(Color.WHITE);

        String shootKey = getKeyName(GameConfig.Action.SHOOT);
        String leftKey = getKeyName(GameConfig.Action.LEFT);
        String rightKey = getKeyName(GameConfig.Action.RIGHT);
        String pauseKey = getKeyName(GameConfig.Action.PAUSE);

        String controlsText = String.format("%s: Shoot  |  %s / %s: Move  |  %s: Pause",
                shootKey, leftKey, rightKey, pauseKey);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(controlsText, Game.WIDTH / 2, Game.HEIGHT - 10);
        gc.setTextAlign(TextAlignment.LEFT); // Reset pour le reste
    }

    private void drawTextWithGlow(GraphicsContext gc, String text, double x, double y, Color color) {
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillText(text, x + 2, y + 2);
        gc.setFill(color);
        gc.fillText(text, x, y);
    }

    private void drawPowerUpIcon(GraphicsContext gc, double x, double y, Color color, String name) {
        gc.setFill(color);
        gc.fillOval(x, y, 15, 15);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeOval(x, y, 15, 15);
        gc.setFill(Color.WHITE);
        gc.fillText(name, x + 75, y + 12);
    }

    public void drawMenu(GraphicsContext gc) {
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        // Note: Pour le menu on suppose des positions fixes ou on centre
        gc.setTextAlign(TextAlignment.CENTER); // Utiliser l'alignement centré simplifie tout
        drawTextWithGlow(gc, "SPACE INVADERS", Game.WIDTH / 2, 150, Color.CYAN);

        gc.setFont(regularFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("~ Defend Earth from Alien Invasion ~", Game.WIDTH / 2, 200);

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gc.setFill(Color.WHITE);
        String startKey = getKeyName(GameConfig.Action.SELECT); // Touche entrée ou autre
        gc.fillText("Press " + startKey + " to Start", Game.WIDTH / 2, Game.HEIGHT / 2);

        gc.setFont(smallFont);
        gc.setFill(Color.LIGHTGRAY);
        int y = Game.HEIGHT / 2 + 80;

        gc.fillText("CONTROLS:", Game.WIDTH / 2, y);

        String moveKeys = getKeyName(GameConfig.Action.LEFT) + " / " + getKeyName(GameConfig.Action.RIGHT);
        gc.fillText(moveKeys + " : Move Ship", Game.WIDTH / 2, y + 25);

        String shootKey = getKeyName(GameConfig.Action.SHOOT);
        gc.fillText(shootKey + " : Shoot", Game.WIDTH / 2, y + 45);

        String pauseKey = getKeyName(GameConfig.Action.PAUSE);
        gc.fillText(pauseKey + " : Pause Game", Game.WIDTH / 2, y + 65);

        gc.setFill(Color.YELLOW);
        y += 120;
        gc.fillText("COLLECT POWER-UPS:", Game.WIDTH / 2, y);

        gc.setTextAlign(TextAlignment.LEFT); // Reset pour dessiner les icônes
        drawPowerUpInfo(gc, Game.WIDTH / 2 - 150, y + 20, Color.CYAN, "Shield");
        drawPowerUpInfo(gc, Game.WIDTH / 2 + 30, y + 20, Color.RED, "Rapid Fire");
        drawPowerUpInfo(gc, Game.WIDTH / 2 - 150, y + 50, Color.PURPLE, "Triple Shot");
        drawPowerUpInfo(gc, Game.WIDTH / 2 + 30, y + 50, Color.YELLOW, "Speed Boost");
    }

    private void drawPowerUpInfo(GraphicsContext gc, double x, double y, Color color, String name) {
        gc.setFill(color);
        gc.fillOval(x, y, 12, 12);
        gc.setStroke(Color.WHITE);
        gc.strokeOval(x, y, 12, 12);
        gc.setFill(Color.WHITE);
        gc.fillText(name, x + 18, y + 10);
    }

    public void drawPause(GraphicsContext gc, int score, int level) {
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        gc.setTextAlign(TextAlignment.CENTER);

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        drawTextWithGlow(gc, "PAUSED", Game.WIDTH / 2, Game.HEIGHT / 2 - 50, Color.YELLOW);

        gc.setFont(regularFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + String.format("%06d", score), Game.WIDTH / 2, Game.HEIGHT / 2 + 20);
        gc.fillText("Level: " + level, Game.WIDTH / 2, Game.HEIGHT / 2 + 50);

        String pauseKey = getKeyName(GameConfig.Action.PAUSE);
        String menuKey = getKeyName(GameConfig.Action.SELECT); // Touche pour valider le menu

        gc.setFont(smallFont);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Press " + pauseKey + " to Resume", Game.WIDTH / 2, Game.HEIGHT / 2 + 100);

        gc.fillText("Use Arrows & " + menuKey + " to Navigate", Game.WIDTH / 2, Game.HEIGHT / 2 + 120);

        gc.setTextAlign(TextAlignment.LEFT);
    }

    public void drawGameOver(GraphicsContext gc, int finalScore, int level) {
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        gc.setTextAlign(TextAlignment.CENTER);

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        drawTextWithGlow(gc, "GAME OVER", Game.WIDTH / 2, Game.HEIGHT / 2 - 80, Color.RED);

        gc.setFont(titleFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("FINAL SCORE: " + String.format("%06d", finalScore),
                Game.WIDTH / 2, Game.HEIGHT / 2);
        gc.fillText("LEVEL REACHED: " + level, Game.WIDTH / 2, Game.HEIGHT / 2 + 40);

        String continueKey = getKeyName(GameConfig.Action.SELECT);

        gc.setFont(regularFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Press " + continueKey + " to Continue", Game.WIDTH / 2, Game.HEIGHT / 2 + 120);

        gc.setTextAlign(TextAlignment.LEFT);
    }

    public void drawVictory(GraphicsContext gc, int finalScore) {
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        gc.setTextAlign(TextAlignment.CENTER);

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        double glowIntensity = 0.5 + Math.sin(System.currentTimeMillis() * 0.005) * 0.5;
        Color glowColor = Color.rgb((int)(255 * glowIntensity), 215, 0);
        drawTextWithGlow(gc, "VICTORY!", Game.WIDTH / 2, Game.HEIGHT / 2 - 80, glowColor);

        gc.setFont(regularFont);
        gc.setFill(Color.CYAN);
        gc.fillText("You have defeated the alien invasion!",
                Game.WIDTH / 2, Game.HEIGHT / 2 - 20);

        gc.setFont(titleFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("FINAL SCORE: " + String.format("%06d", finalScore),
                Game.WIDTH / 2, Game.HEIGHT / 2 + 40);

        String continueKey = getKeyName(GameConfig.Action.SELECT);

        gc.setFont(regularFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Press " + continueKey + " to Main Menu", Game.WIDTH / 2, Game.HEIGHT / 2 + 120);

        gc.setTextAlign(TextAlignment.LEFT);
    }
}