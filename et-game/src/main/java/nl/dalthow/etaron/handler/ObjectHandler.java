/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class ObjectHandler.java
 *
 **/

package nl.dalthow.etaron.handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.framework.Identifier;
import nl.dalthow.etaron.framework.State;
import nl.dalthow.etaron.framework.WorldObject;
import nl.dalthow.etaron.object.Block;
import nl.dalthow.etaron.object.Item;
import nl.dalthow.etaron.object.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ObjectHandler 
{
    // Declaration

    private BufferedImage currentLevel;
    
    private WorldObject temporaryObject;
    private Player temporaryPlayer;
  
    @Autowired
    private ApplicationContext applicationContext;

    public LinkedList<WorldObject> objects = new LinkedList<>();
    
           
    // Constructor
    
    private ObjectHandler() 
    {
        
    }


    // Makes all the world objects tick
    
    public void tick() 
    {
        for(int i = 0; i < objects.size(); i++) 
        {
            temporaryObject = objects.get(i);
            
            if(temporaryObject.getId() == Identifier.PLAYER)
            {
            	temporaryPlayer = (Player)temporaryObject;
            	temporaryPlayer.tick(objects);
            }
            
            else if(temporaryObject.getId() == Identifier.BLOCK || temporaryObject.getId() == Identifier.LAVA)
            {
            	if(temporaryPlayer != null && temporaryPlayer.getUpdateBounds().intersects(temporaryObject.getBounds()))
            	{
            		temporaryObject.tick(objects);
            	}
            }
            
            else
            {
                temporaryObject.tick(objects);
            }
        }
    }


    // Makes all the world objects render
    
    public void render(Graphics graphicsObject) 
    {
        for(WorldObject object : objects) 
        {
            object.render(graphicsObject);
        }
    }


    // Removes all the objects
   
    public void clearLevel() 
    {
        objects.clear();
        
        Main.cameraObject.setPosX(0); 
        Main.cameraObject.setPosY(0);
       
        Main.levelScore = 0;
    }


    // Loads the requested level based on a image
    
    public void loadLevel(BufferedImage image) 
    {
    	Main.loadTimeRemaining = 250;
    	
        clearLevel();

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        currentLevel = image;

        for(int i = 0; i < imageHeight; i++) 
        {
            for(int j = 0; j < imageWidth; j++) 
            {
                int currentPixel = image.getRGB(i, j);

                int red = (currentPixel >> 16) & 0xff;
                int green = (currentPixel >> 8) & 0xff;
                int blue = (currentPixel) & 0xff;

		        if(red == 100 && green == 100 && blue == 100)
		        {
		            addObject(new Block((i * 32), (j * 32), 5, 0, 0, Identifier.DECOR));
		        }
            }
        }
        
        for(int i = 0; i < imageHeight; i++) 
        {
            for(int j = 0; j < imageWidth; j++) 
            {
                int currentPixel = image.getRGB(i, j);

                int red = (currentPixel >> 16) & 0xff;
                int green = (currentPixel >> 8) & 0xff;
                int blue = (currentPixel) & 0xff;

                if(red == 255 && green == 255 && blue == 255)
                {
                    addObject(new Block((i * 32), (j * 32), 0, 0, 0, Identifier.BLOCK));
                }

                else if(red == 255 && green == 0 && blue == 0) 
                {
                    addObject(new Block((i * 32), (j * 32), 1, 0, 0, Identifier.LAVA));
                }

                else if(red == 0 && green == 255 && blue == 255)
                {
                    addObject(new Block((i * 32), (j * 32), 3, 1, 8, Identifier.BLOCK));
                }
                
                else if(red == 255 && green == 0 && blue == 255) 
                {
                    addObject(new Block((i * 32), (j * 32), 4, 0, 8, Identifier.BLOCK));
                }

                else if(red == 255 && green == 175 && blue == 175)
                {
                    addObject(new Block((i * 32), (j * 32), 6, 0, 0, Identifier.TRAMPOLINE));
                }
                
                else if(red == 155 && green == 75 && blue == 0) 
                {
                	addObject(new Block((i * 32), (j * 32), 7, 0, 0, Identifier.DOOR));
                }

                else if(red == 255 && green == 255 && blue == 0)
                {
                    addObject(new Item((i * 32), (j * 32), 0, Identifier.COIN));
                }
                
                else if(red == 175 && green == 175 && blue == 175)
                {
                    addObject(new Item((i * 32), (j * 32), 0, Identifier.KEY));
                }

                else if(red == 0 && green == 255 && blue == 0) 
                {
                	addObject(new Block((i * 32), (j * 32), 2, 0, 0, Identifier.FLAG));
                }
                
                else if(red == 255 && green == 200 && blue == 0) 
                {
                    addObject((WorldObject)applicationContext.getBean("turret", (i * 32), (j * 32 - 8), 0, Identifier.TURRET));
                }

                else if(red == 0 && green == 0 && blue == 255) 
                {
                    addObject((WorldObject)applicationContext.getBean("player", (i * 32), (j * 32), Identifier.PLAYER));
                }
            }
        }           
        
        for(int i = 0; i < objects.size(); i++) 
        {
            WorldObject temporaryObject = objects.get(i);
        	
            if(temporaryObject.getId() == Identifier.PLAYER) 
            {
                Main.setCameraFocus(objects.get(i));
                KeyHandler.cameraShouldFocus = objects.get(i);
                
                Main.cameraObject.tick(Main.getCameraFocus());
            }
        }
        
        Main.currentState = State.GAME;
    }


    // Reload level

    public void reloadLevel()
    {
        loadLevel(currentLevel);
    }


    // Adds a object from the list

    public void addObject(WorldObject object) 
    {
        objects.add(object);
    }

    
    // Removes a object from the list

    public void removeObject(WorldObject object) 
    {
        objects.remove(object);
    }
}