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

        Game game = new Game();

        game.getInputHandler().attachToScene(scene);
        SoundManager.getInstance().startMusic("/tn/client/space_invaders/sounds/spaceinvaders1.mp3");

        primaryStage.setTitle("Space Invaders - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);  // Enable maximize button

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double scaleX = scene.getWidth() / Game.WIDTH;
            double scaleY = scene.getHeight() / Game.HEIGHT;

            canvas.setScaleX(scaleX);
            canvas.setScaleY(scaleY);
        };

        scene.widthProperty().addListener(stageSizeListener);
        scene.heightProperty().addListener(stageSizeListener);

        primaryStage.show();

        root.requestFocus();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        game.start(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}