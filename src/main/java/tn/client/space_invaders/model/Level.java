package tn.client.space_invaders.model;


public class Level {

    public final int levelNumber;
    public final int numRows;
    public final int numCols;
    public final int startX, startY;
    public final int colSpacing, rowSpacing;
    public final int enemySpeed; // New!
    public final double enemyFireRate; // New!
    public final int scorePerEnemy;

    public Level(int levelNumber, int rows, int cols, int startX, int startY,
                 int colSpacing, int rowSpacing, int speed, double fireRate, int score) {
        this.levelNumber = levelNumber;
        this.numRows = rows;
        this.numCols = cols;
        this.startX = startX;
        this.startY = startY;
        this.colSpacing = colSpacing;
        this.rowSpacing = rowSpacing;
        this.enemySpeed = speed;
        this.enemyFireRate = fireRate;
        this.scorePerEnemy = score;

    }
}
