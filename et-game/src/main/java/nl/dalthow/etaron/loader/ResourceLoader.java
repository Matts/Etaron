/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class SongResource.java
 * 
 **/

package nl.dalthow.etaron.loader;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceLoader 
{
    // Declaration
	
	@Autowired
    private ApplicationContext applicationContext;

    private Map<ImageResource, Resource> images = new HashMap<>(ImageResource.values().length);
    private Map<FontResource, Resource> fonts = new HashMap<>(FontResource.values().length);
    private Map<SoundResource, Resource> sounds = new HashMap<>(MusicResource.values().length);
    private Map<MusicResource, Resource> songs = new HashMap<>(MusicResource.values().length);

    
    // Loads the actual files 
    
    @PostConstruct
    public void loadResources() 
    {
        for(ImageResource image : ImageResource.values())
        {
            images.put(image, applicationContext.getResource(image.getPath()));
        }
        
        for(FontResource font : FontResource.values()) 
        {
            fonts.put(font, applicationContext.getResource(font.getPath()));
        }
        
        for(SoundResource sound : SoundResource.values()) 
        {
            sounds.put(sound, applicationContext.getResource(sound.getPath()));
        }
        
        for(MusicResource song : MusicResource.values()) 
        {
            songs.put(song, applicationContext.getResource(song.getPath()));
        }
    }

    
    // Getters
    
    public BufferedImage get(ImageResource image) throws IOException 
    {
        return ImageIO.read(images.get(image).getFile());
    }

    public Font get(FontResource font) throws IOException, FontFormatException 
    {
        return Font.createFont(Font.TRUETYPE_FONT, fonts.get(font).getInputStream());
    }

    public InputStream get(MusicResource music) throws IOException 
    {
        return songs.get(music).getInputStream();
    }

    public InputStream get(SoundResource sound) throws IOException 
    {
        return sounds.get(sound).getInputStream();
    }
}
