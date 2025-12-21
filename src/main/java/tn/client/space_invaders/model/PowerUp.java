package tn.client.space_invaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tn.client.space_invaders.patterns.decorator.Player;

public class PowerUp extends GameObject {

    public enum PowerUpType {
        RAPID_FIRE,
        SHIELD,
        TRIPLE_SHOT,
//        DOUBLE_GUN,
        SPEED_BOOST
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
        if (y > 600) active = false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getBackgroundColor());
        gc.fillOval(x, y, width, height);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeOval(x, y, width, height);

        gc.setFill(Color.WHITE);
        switch (type) {
            case RAPID_FIRE:
                // Icône balle unique grosse
                gc.fillRect(x + width * 0.4, y + height * 0.2, width * 0.2, height * 0.6);
                break;

            case SHIELD:
                // Icône rond vide
                gc.strokeOval(x + width * 0.25, y + height * 0.25, width * 0.5, height * 0.5);
                break;

            case TRIPLE_SHOT:
                // 3 petits traits
                gc.fillRect(x + width * 0.2, y + height * 0.3, width * 0.1, height * 0.4);
                gc.fillRect(x + width * 0.45, y + height * 0.3, width * 0.1, height * 0.4);
                gc.fillRect(x + width * 0.7, y + height * 0.3, width * 0.1, height * 0.4);
                break;

            case SPEED_BOOST:
                // Flèche vers le haut
                double[] arrowX = {x + width * 0.5, x + width * 0.2, x + width * 0.8};
                double[] arrowY = {y + height * 0.2, y + height * 0.6, y + height * 0.6};
                gc.fillPolygon(arrowX, arrowY, 3);
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

    public PowerUpType getType() { return type; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean checkCollision(Player player) {
        return x < player.getX() + player.getWidth() &&
                x + width > player.getX() &&
                y < player.getY() + player.getHeight() &&
                y + height > player.getY();
    }
}