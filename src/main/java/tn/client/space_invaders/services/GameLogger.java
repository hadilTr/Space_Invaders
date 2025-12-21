package tn.client.space_invaders.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class GameLogger {

    private static GameLogger instance;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private GameLogger() {
    }

    public static GameLogger getInstance() {
        if (instance == null) {
            instance = new GameLogger();
        }
        return instance;
    }


    public void info(String message) {
        log("INFO", message);
    }

    public void warning(String message) {
        log("WARN", message);
    }

    public void error(String message) {
        System.err.println("[" + getTimestamp() + "] [ERROR] " + message);
    }

    private void log(String level, String message) {
        System.out.println("[" + getTimestamp() + "] [" + level + "] " + message);
    }

    private String getTimestamp() {
        return LocalDateTime.now().format(formatter);
    }
}