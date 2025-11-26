package tn.client.space_invaders.main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tn.client.space_invaders.core.Game;
import tn.client.space_invaders.services.SoundManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(Game.WIDTH, Game.HEIGHT);
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, Game.WIDTH, Game.HEIGHT);

        // Création du jeu
        Game game = new Game();

        // IMPORTANT : On branche le clavier sur la scène
        game.getInputHandler().attachToScene(scene);
        SoundManager.getInstance().startMusic("/tn/client/space_invaders/sounds/spaceinvaders1.mp3");

        // Setup stage BEFORE starting game
        primaryStage.setTitle("Space Invaders - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);  // Enable maximize button

        // Stretch canvas to fill entire window (may distort aspect ratio)
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double scaleX = scene.getWidth() / Game.WIDTH;
            double scaleY = scene.getHeight() / Game.HEIGHT;

            // Use both scales independently to fill the window completely
            canvas.setScaleX(scaleX);
            canvas.setScaleY(scaleY);
        };

        scene.widthProperty().addListener(stageSizeListener);
        scene.heightProperty().addListener(stageSizeListener);

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