package tn.client.space_invaders.model;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.patterns.decorator.Player;

public class PowerUp extends GameObject {

    public enum PowerUpType {
        RAPID_FIRE,      // Faster shooting
        SHIELD,          // Temporary invincibility
        TRIPLE_SHOT,     // Shoot 3 bullets at once
        SPEED_BOOST      // Move faster
    }

    private PowerUpType type;
    private int fallSpeed = 2;
    private boolean active = true;

    public PowerUp(int x, int y, PowerUpType type) {
        super(x, y, 30, 30);
        this.type = type;
    }

    @Override
    public void update() {
        y += fallSpeed;

        // Deactivate if falls off screen
        if (y > 600) {
            active = false;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Draw background circle
        gc.setFill(getBackgroundColor());
        gc.fillOval(x, y, width, height);

        // Draw border
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeOval(x, y, width, height);

        // Draw icon based on type
        gc.setFill(Color.WHITE);
        switch (type) {
            case RAPID_FIRE:
                // Draw bullets icon
                gc.fillRect(x + width * 0.4, y + height * 0.2, width * 0.2, height * 0.6);
                gc.fillOval(x + width * 0.35, y + height * 0.15, width * 0.3, height * 0.15);
                break;

            case SHIELD:
                // Draw shield icon
                gc.strokeOval(x + width * 0.2, y + height * 0.2, width * 0.6, height * 0.6);
                gc.strokeOval(x + width * 0.25, y + height * 0.25, width * 0.5, height * 0.5);
                break;

            case TRIPLE_SHOT:
                // Draw 3 bullets
                gc.fillRect(x + width * 0.2, y + height * 0.3, width * 0.15, height * 0.4);
                gc.fillRect(x + width * 0.425, y + height * 0.2, width * 0.15, height * 0.6);
                gc.fillRect(x + width * 0.65, y + height * 0.3, width * 0.15, height * 0.4);
                break;

            case SPEED_BOOST:
                // Draw arrow/wings
                double[] arrowX = {x + width * 0.5, x + width * 0.2, x + width * 0.5, x + width * 0.8};
                double[] arrowY = {y + height * 0.2, y + height * 0.5, y + height * 0.4, y + height * 0.5};
                gc.fillPolygon(arrowX, arrowY, 4);
                double[] arrow2Y = {y + height * 0.5, y + height * 0.8, y + height * 0.7, y + height * 0.8};
                gc.fillPolygon(arrowX, arrow2Y, 4);
                break;
        }
    }

    private Color getBackgroundColor() {
        switch (type) {
            case RAPID_FIRE: return Color.RED;
            case SHIELD: return Color.CYAN;
            case TRIPLE_SHOT: return Color.PURPLE;
            case SPEED_BOOST: return Color.YELLOW;
            default: return Color.GREEN;
        }
    }

    public PowerUpType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean checkCollision(Player player) {
        return x < player.getX() + player.getWidth() &&
                x + width > player.getX() &&
                y < player.getY() + player.getHeight() &&
                y + height > player.getY();
    }
}
