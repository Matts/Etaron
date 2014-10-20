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
    private Player firstPlayer;
    private Player secondPlayer;
    
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
            	if(firstPlayer == null)
                {
                	firstPlayer = (Player) temporaryObject;
                }
                
                else if(secondPlayer == null)
                {
                	secondPlayer = (Player) temporaryObject;
                }
            }
            
            else
            {
            	
            }

            temporaryObject.tick(objects);
        }
    }


    // Makes all the world objects render
    
    public void render(Graphics graphicsObject) 
    {
    	for(int i = 0; i < objects.size(); i++) 
        {
            temporaryObject = objects.get(i);
               
        	if(firstPlayer != null)
        	{
        		firstPlayer.render(graphicsObject);
        	}
        	
        	if(secondPlayer != null)
        	{
        		secondPlayer.render(graphicsObject);
        	}	
        	
        	if(firstPlayer != null && firstPlayer.getRenderBounds().intersects(temporaryObject.getBounds()))
        	{
        		temporaryObject.render(graphicsObject);
        	}
        	
        	else if(secondPlayer != null && secondPlayer.getRenderBounds().intersects(temporaryObject.getBounds()))
        	{
        		temporaryObject.render(graphicsObject);
        	}
        }
    }


    // Removes all the objects
   
    public void clearLevel() 
    {
        objects.clear();
        
        firstPlayer = null;
        secondPlayer = null;
        
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
		            addObject(new Block((i * 32), (j * 32), 5, 0, 0, Identifier.DECOR, false));
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
                    addObject(new Block((i * 32), (j * 32), 0, 0, 0, Identifier.BLOCK, true));
                }

                else if(red == 255 && green == 0 && blue == 0) 
                {
                    addObject(new Block((i * 32), (j * 32), 1, 0, 0, Identifier.LAVA, false));
                }

                else if(red == 0 && green == 255 && blue == 255)
                {
                    addObject(new Block((i * 32), (j * 32), 3, 1, 8, Identifier.PLATFORM, true));
                }
                
                else if(red == 255 && green == 0 && blue == 255) 
                {
                    addObject(new Block((i * 32), (j * 32), 4, 0, 8, Identifier.PLATFORM, true));
                }

                else if(red == 255 && green == 175 && blue == 175)
                {
                    addObject(new Block((i * 32), (j * 32), 6, 0, 0, Identifier.TRAMPOLINE, false));
                }
                
                else if(red == 155 && green == 75 && blue == 0) 
                {
                	addObject(new Block((i * 32), (j * 32), 7, 0, 0, Identifier.DOOR, false));
                }

                else if(red == 255 && green == 255 && blue == 0)
                {
                    addObject(new Item((i * 32), (j * 32), 0, Identifier.COIN, false));
                }
                
                else if(red == 175 && green == 175 && blue == 175)
                {
                    addObject(new Item((i * 32), (j * 32), 0, Identifier.KEY, false));
                }

                else if(red == 0 && green == 255 && blue == 0) 
                {
                	addObject(new Block((i * 32), (j * 32), 2, 0, 0, Identifier.FLAG, false));
                }
                
                else if(red == 255 && green == 200 && blue == 0) 
                {
                    addObject((WorldObject)applicationContext.getBean("turret", (i * 32), (j * 32 - 8), 0, Identifier.TURRET, true));
                }

                else if(red == 0 && green == 0 && blue == 255) 
                {
                    addObject((WorldObject)applicationContext.getBean("player", (i * 32), (j * 32), Identifier.PLAYER, false));
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