/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios
 * @Class SoundHandler.java
 *
 **/

package nl.dalthow.etaron.handler;

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.loader.ResourceLoader;
import nl.dalthow.etaron.loader.MusicResource;
import nl.dalthow.etaron.loader.SoundResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;

import java.io.IOException;

@Component
public class SoundHandler 
{
    // Declaration
	
    private static final Logger logger = LoggerFactory.getLogger(SoundHandler.class);

    @Autowired
    private ResourceLoader resourceLoader;

    public Clip musicClip;
    public Clip soundClip;


    // Can be called from anywhere to load a music clip
    
    public void loadSong(MusicResource music) 
    {
        try 
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resourceLoader.get(music));

            musicClip = AudioSystem.getClip();
            musicClip.open(audioInputStream);

            changeVolume(Main.musicVolume, musicClip);
        } 
        
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException error) 
        {
            logger.error(error.getMessage(), error);
        }
    }


    // Can be called from anywhere to load a sound clip
    
    public void loadSound(SoundResource sound) 
    {
        try 
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resourceLoader.get(sound));

            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);

            changeVolume(Main.soundVolume, soundClip);
        } 
        
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException error) 
        {
            logger.error(error.getMessage(), error);
        }
    }


    // Adjust the volume of the currently playing clip
   
    public void changeVolume(float adjustment, Clip clip) 
    {
        int convertedAdjustment = (int) (-(10 - adjustment) * 4);

        if(adjustment == 0) 
        {
            muteVolume(clip);
        } 
        
        else 
        {
            FloatControl clipController = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clipController.setValue(convertedAdjustment);
        }
    }


    // Mute the currently playing clip

    public void muteVolume(Clip clip) 
    {
        changeVolume(-10F, clip);
    }
}
