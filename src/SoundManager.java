import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundManager {

    private static SoundManager instance;
    private Main main;

    // Statische Methode für den Zugriff
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private Clip backgroundClip;

    public void playSound(String path, float volume) {
        new Thread(() -> {
            try {
                Clip clip = loadClip(path);
                if (clip != null) {
                    setVolume(clip, volume);
                    clip.start();

                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }
            } catch (Exception e) {
                main.printErrOnDebug("Fehler beim Abspielen von " + path + ": " + e.getMessage());
            }
        }).start();
    }

    // 2. Musik/Sound im nahtlosen Endlos-Loop starten
    public void startLoop(String path, float volume) {
        stopLoop();

        try {
            backgroundClip = loadClip(path);
            if (backgroundClip != null) {
                setVolume(backgroundClip, volume);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Nahtloser WAV-Loop
                backgroundClip.start();
            }
        } catch (Exception e) {
            main.printErrOnDebug("Fehler beim Starten des Loops " + path + ": " + e.getMessage());
        }
    }

    // 3. Laufenden Hintergrund-Loop stoppen
    public void stopLoop() {
        if (backgroundClip != null) {
            if (backgroundClip.isRunning()) {
                backgroundClip.stop();
            }
            backgroundClip.close();
            backgroundClip = null;
        }
    }

    // 4. Lautstärke des aktiven Loops dynamisch ändern (0.0 = stumm, 1.0 = voll)
    public void setLoopVolume(float volume) {
        if (backgroundClip != null) {
            setVolume(backgroundClip, volume);
        }
    }

    // 5. Prüfen, ob aktuell Musik im Loop läuft
    public boolean isLoopRunning() {
        return backgroundClip != null && backgroundClip.isRunning();
    }

    // Internes Anpassen des Dezibel-Werts (Master Gain)
    private void setVolume(Clip clip, float volume) {
        if (clip == null) return;

        volume = Math.max(0.0f, Math.min(1.0f, volume)); // Begrenzung auf 0.0 - 1.0

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Umrechnung von linearer Skala in Dezibel
            float dB = (float) (Math.log10(volume == 0 ? 0.0001 : volume) * 20.0);
            gainControl.setValue(dB);
        }
    }

    // Lädt die WAV-Datei direkt aus dem Resources-Ordner
    private Clip loadClip(String path) throws Exception {
        InputStream audioSrc = getClass().getResourceAsStream(path);
        if (audioSrc == null) {
            main.printErrOnDebug("WAV-Datei nicht gefunden unter: " + path);
            return null;
        }

        InputStream bufferedIn = new BufferedInputStream(audioSrc);
        AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);

        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        ais.close();
        return clip;
    }

    public void initMain(Main main) {
        this.main = main;
    }
}
