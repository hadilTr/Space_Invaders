package tn.client.space_invaders.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tn.client.space_invaders.core.Game;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(Game.WIDTH, Game.HEIGHT);
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);

        // Création du jeu
        Game game = new Game();

        // IMPORTANT : On branche le clavier sur la scène
        game.getInputHandler().attachToScene(scene);

        // Setup stage BEFORE starting game
        primaryStage.setTitle("Space Invaders - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Request focus AFTER showing the stage
        root.requestFocus();

        // Lancement du jeu en dernier
        GraphicsContext gc = canvas.getGraphicsContext2D();
        game.start(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}