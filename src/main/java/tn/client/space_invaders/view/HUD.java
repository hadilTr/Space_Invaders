package tn.client.space_invaders.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tn.client.space_invaders.core.Game;

public class HUD {

    private Font titleFont;
    private Font regularFont;
    private Font smallFont;

    public HUD() {
        titleFont = Font.font("Arial", FontWeight.BOLD, 32);
        regularFont = Font.font("Arial", FontWeight.BOLD, 20);
        smallFont = Font.font("Arial", FontWeight.NORMAL, 14);
    }

    public void drawGameHUD(GraphicsContext gc, int score, int level,
                            boolean hasShield, boolean hasRapidFire,
                            boolean hasTripleShot, boolean hasSpeedBoost) {

        // Draw semi-transparent panel at top
        gc.setFill(Color.rgb(0, 0, 0, 0.6));
        gc.fillRect(0, 0, Game.WIDTH, 70);

        // Draw score with glow effect
        gc.setFont(regularFont);
        drawTextWithGlow(gc, "SCORE", 20, 25, Color.CYAN);
        gc.setFont(titleFont);
        drawTextWithGlow(gc, String.format("%06d", score), 20, 55, Color.WHITE);

        // Draw level
        gc.setFont(regularFont);
        drawTextWithGlow(gc, "LEVEL", Game.WIDTH / 2 - 40, 25, Color.LIME);
        gc.setFont(titleFont);
        drawTextWithGlow(gc, String.valueOf(level), Game.WIDTH / 2 - 15, 55, Color.WHITE);

        // Draw active power-ups on the right
        int powerUpX = Game.WIDTH - 200;
        int powerUpY = 15;

        gc.setFont(smallFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("ACTIVE POWER-UPS:", powerUpX, powerUpY);

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

        // Draw bottom info bar
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, Game.HEIGHT - 30, Game.WIDTH, 30);

        gc.setFont(smallFont);
        gc.setFill(Color.WHITE);
        gc.fillText("SPACE: Shoot  |  ← →: Move  |  ESC: Pause", 20, Game.HEIGHT - 10);
    }

    private void drawTextWithGlow(GraphicsContext gc, String text, double x, double y, Color color) {
        // Draw glow/shadow
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillText(text, x + 2, y + 2);

        // Draw main text
        gc.setFill(color);
        gc.fillText(text, x, y);
    }

    private void drawPowerUpIcon(GraphicsContext gc, double x, double y, Color color, String name) {
        // Draw small circle icon
        gc.setFill(color);
        gc.fillOval(x, y, 15, 15);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeOval(x, y, 15, 15);

        // Draw name
        gc.setFill(Color.WHITE);
        gc.fillText(name, x + 20, y + 12);
    }

    public void drawMenu(GraphicsContext gc) {
        // Title with glow
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        drawTextWithGlow(gc, "SPACE INVADERS", Game.WIDTH / 2 - 250, 150, Color.CYAN);

        // Subtitle
        gc.setFont(regularFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("~ Defend Earth from Alien Invasion ~", Game.WIDTH / 2 - 180, 200);

        // Instructions
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gc.setFill(Color.WHITE);
        gc.fillText("Press ENTER to Start", Game.WIDTH / 2 - 120, Game.HEIGHT / 2);

        // Controls
        gc.setFont(smallFont);
        gc.setFill(Color.LIGHTGRAY);
        int y = Game.HEIGHT / 2 + 80;
        gc.fillText("CONTROLS:", Game.WIDTH / 2 - 50, y);
        gc.fillText("← → : Move Ship", Game.WIDTH / 2 - 70, y + 25);
        gc.fillText("SPACE : Shoot", Game.WIDTH / 2 - 70, y + 45);
        gc.fillText("ESC : Pause Game", Game.WIDTH / 2 - 70, y + 65);

        // Power-ups info
        gc.setFill(Color.YELLOW);
        y += 120;
        gc.fillText("COLLECT POWER-UPS:", Game.WIDTH / 2 - 90, y);

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
        // Semi-transparent overlay
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        // Paused text
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        drawTextWithGlow(gc, "PAUSED", Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 50, Color.YELLOW);

        // Stats
        gc.setFont(regularFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + String.format("%06d", score), Game.WIDTH / 2 - 70, Game.HEIGHT / 2 + 20);
        gc.fillText("Level: " + level, Game.WIDTH / 2 - 40, Game.HEIGHT / 2 + 50);

        // Instructions
        gc.setFont(smallFont);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Press ESC to Resume", Game.WIDTH / 2 - 80, Game.HEIGHT / 2 + 100);
        gc.fillText("Press ENTER to Main Menu", Game.WIDTH / 2 - 100, Game.HEIGHT / 2 + 120);
    }

    public void drawGameOver(GraphicsContext gc, int finalScore, int level) {
        // Semi-transparent overlay
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        // Game Over text
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        drawTextWithGlow(gc, "GAME OVER", Game.WIDTH / 2 - 200, Game.HEIGHT / 2 - 80, Color.RED);

        // Final stats
        gc.setFont(titleFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("FINAL SCORE: " + String.format("%06d", finalScore),
                Game.WIDTH / 2 - 160, Game.HEIGHT / 2);
        gc.fillText("LEVEL REACHED: " + level, Game.WIDTH / 2 - 140, Game.HEIGHT / 2 + 40);

        // Continue
        gc.setFont(regularFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Press ENTER to Continue", Game.WIDTH / 2 - 130, Game.HEIGHT / 2 + 120);
    }

    public void drawVictory(GraphicsContext gc, int finalScore) {
        // Semi-transparent overlay
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        // Victory text with animation glow
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        double glowIntensity = 0.5 + Math.sin(System.currentTimeMillis() * 0.005) * 0.5;
        Color glowColor = Color.rgb((int)(255 * glowIntensity), 215, 0);
        drawTextWithGlow(gc, "VICTORY!", Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 80, glowColor);

        // Congratulations
        gc.setFont(regularFont);
        gc.setFill(Color.CYAN);
        gc.fillText("You have defeated the alien invasion!",
                Game.WIDTH / 2 - 180, Game.HEIGHT / 2 - 20);

        // Final score
        gc.setFont(titleFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("FINAL SCORE: " + String.format("%06d", finalScore),
                Game.WIDTH / 2 - 160, Game.HEIGHT / 2 + 40);

        // Continue
        gc.setFont(regularFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Press ENTER to Main Menu", Game.WIDTH / 2 - 130, Game.HEIGHT / 2 + 120);
    }
}