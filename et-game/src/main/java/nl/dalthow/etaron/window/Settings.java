/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Settings.java
 *
 **/

package nl.dalthow.etaron.window;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.handler.SoundHandler;
import nl.dalthow.etaron.service.AccessClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Settings 
{
    // Declaration

	private static final Logger logger = LoggerFactory.getLogger(Settings.class);

    private JPanel settingsContent;
   
    private JLabel musicVolumeLabel;
    private JLabel soundVolumeLabel;
   
    public static JSlider musicVolumeSlider;
    public static JSlider soundVolumeSlider;

    public static JFrame frame;

    @Autowired
    private SoundHandler soundHandler;

    @Autowired
    private AccessClient accessClient;

    
    // Creates the window where everything gets projected on
    
    @Autowired
    private Settings(@Value("${settings.width}") int width, @Value("${settings.height}") int height, @Value("${settings.title}") String title) 
    {
        frame = new JFrame(title);

        settingsContent = new JPanel();
        
        musicVolumeLabel = new JLabel("Music");
        soundVolumeLabel = new JLabel("Sound");
        				
        musicVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
        soundVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);

        frame.pack();
        frame.add(settingsContent);

        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
        
        Image image = Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("global/icon.png"));
        
        frame.setIconImage(image);
    }

    // Initialises all the components used in the window after spring creates the instance
    
    @PostConstruct
    private void placeComponents() 
    {
        frame.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event) 
            {
                if(Main.user != null) 
                {
                    Main.user.getSettings().setMusicVolume(musicVolumeSlider.getValue());
                    Main.user.getSettings().setSoundVolume(soundVolumeSlider.getValue());
                    accessClient.saveSettings(Main.user);

                    logger.debug("Saved settings to database, closing settings window now.");
                } 
                
                else 
                {
                    logger.debug("Settings window closed.");
                }
            }
        });
                
        settingsContent.setLayout(null);

        musicVolumeLabel.setBounds(105, 5, 100, 25);
        settingsContent.add(musicVolumeLabel);

        soundVolumeLabel.setBounds(105, 95, 100, 25);
        settingsContent.add(soundVolumeLabel);

        musicVolumeSlider.setMajorTickSpacing(1);
        musicVolumeSlider.setPaintTicks(true);
        musicVolumeSlider.setBounds(15, 30, 215, 45);
        musicVolumeSlider.setValue(Main.musicVolume);
        musicVolumeSlider.addChangeListener(new onMusicSliderChange());
        musicVolumeSlider.setPaintLabels(true);
        settingsContent.add(musicVolumeSlider);

        soundVolumeSlider.setMajorTickSpacing(1);
        soundVolumeSlider.setPaintTicks(true);
        soundVolumeSlider.setBounds(15, 120, 215, 45);
        soundVolumeSlider.setValue(Main.soundVolume);
        soundVolumeSlider.addChangeListener(new onSoundSliderChange());
        soundVolumeSlider.setPaintLabels(true);
        settingsContent.add(soundVolumeSlider);
    }

    public void show() 
    {
        frame.setVisible(true);
        frame.requestFocus();
    }

    private class onMusicSliderChange implements ChangeListener 
    {
        // Gets triggered whenever the sliders position changes

        @Override
        public void stateChanged(ChangeEvent event) 
        {
            Main.musicVolume = musicVolumeSlider.getValue();
            soundHandler.changeVolume(Main.musicVolume, soundHandler.musicClip);
        }
    }

    private class onSoundSliderChange implements ChangeListener 
    {
        // Gets triggered whenever the sliders position changes

        @Override
        public void stateChanged(ChangeEvent event) 
        {
            Main.soundVolume = soundVolumeSlider.getValue();
            soundHandler.changeVolume(Main.soundVolume, soundHandler.soundClip);
        }
    }
}