package manuel_marin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundsEffect {
    public SoundsEffect(String archivo) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(
                    new File(SoundsEffect.class.getResource("sound\\" + archivo).toURI()));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        AudioFormat format = audioInputStream.getFormat();
        var info = new DataLine.Info(Clip.class, format);

        try {
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }

    public void play() {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-5.0f);
        new Thread(() -> {
            clip.setFramePosition(0);
            clip.start();
        }) {
        }.start();
    }

    public void loop() {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-15.0f);
        new Thread(() -> {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }) {
        }.start();
    }

    Clip clip;
    AudioInputStream audioInputStream;
}
