package tn.client.space_invaders.services;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import tn.client.space_invaders.core.GameConfig;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static SoundManager instance;
    private Map<String, AudioClip> soundEffects = new HashMap<>();
    private MediaPlayer backgroundMusic;

    private SoundManager() {

        loadSFX("shoot", "/tn/client/space_invaders/sounds/shoot.wav");
        loadSFX("explosion", "/tn/client/space_invaders/sounds/explosion.wav");
        loadSFX("invaderkilled", "/tn/client/space_invaders/sounds/invaderkilled.wav");

        // Pour le menu, idéalement trouvez un 'select.wav'.
        // Sinon, on utilise invaderkilled temporairement si le fichier n'existe pas.
        loadSFX("select", "/tn/client/space_invaders/sounds/select.wav");
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private void loadSFX(String name, String path) {
        try {
            URL resource = getClass().getResource(path);
            if (resource != null) {
                AudioClip clip = new AudioClip(resource.toExternalForm());
                soundEffects.put(name, clip);
            } else {
                System.err.println("ERREUR SON : Fichier introuvable -> " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Joue un effet sonore (court)
    public void playSFX(String name) {
        AudioClip clip = soundEffects.get(name);
        if (clip != null) {
            // On récupère le volume depuis les options (0.0 à 1.0)
            clip.setVolume(GameConfig.getInstance().getSfxVolume());
            clip.play();
        }
    }

    // Lance la musique de fond (longue)
    public void startMusic(String path) {
        try {
            if (backgroundMusic != null) {
                backgroundMusic.stop(); // Arrête l'ancienne musique si besoin
            }

            URL resource = getClass().getResource(path);
            if (resource != null) {
                Media media = new Media(resource.toExternalForm());
                backgroundMusic = new MediaPlayer(media);
                backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // Boucle infinie
                updateMusicVolume(); // Applique le volume des options
                backgroundMusic.play();
            } else {
                System.err.println("ERREUR MUSIQUE : Fichier introuvable -> " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMusicVolume() {
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(GameConfig.getInstance().getMusicVolume());
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
}