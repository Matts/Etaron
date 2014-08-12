/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class BufferedImageLoader.java
 *
 **/

package nl.dalthow.etaron.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader 
{
    // Declaration
	
    private BufferedImage image;
    
    private static final Logger logger = LoggerFactory.getLogger(BufferedImageLoader.class);


    // Returns a loaded image
    
    public BufferedImage loadImage(String path) 
    {
        try 
        {
            image = ImageIO.read(getClass().getResource(path));
        } 
        
        catch(IOException error) 
        {
            logger.error(error.getMessage(), error);
        }

        return image;
    }
}
