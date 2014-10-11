/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Player.java
 *
 **/

package nl.dalthow.etaron.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.framework.Identifier;
import nl.dalthow.etaron.framework.WorldObject;
import nl.dalthow.etaron.handler.ObjectHandler;
import nl.dalthow.etaron.handler.SoundHandler;
import nl.dalthow.etaron.loader.ResourceLoader;
import nl.dalthow.etaron.loader.SoundResource;
import nl.dalthow.etaron.service.AccessClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Player extends WorldObject 
{
    // Declaration

    private float playerWidth;
    private float playerHeight;
    private float playerGravity;
    
    public boolean hasKey;
   
    @Autowired
    private ObjectHandler handler;

    @Autowired
    private SoundHandler soundHandler;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AccessClient accessClient;

    private final float maximumVelocity;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    
    // Constructor
    
    private Player(float xPos, float yPos, Identifier id, boolean isSolid) 
    {
        super(xPos, yPos, id, isSolid);

        playerWidth = 32F;
        playerHeight = 64F;

        playerGravity = 0.5F;
        maximumVelocity = 15F;
        
        hasKey = false;
    }


    // Gets triggered in the handler and then in the main method

    public void tick(LinkedList<WorldObject> objectList) 
    {
        xPos += xVel;
        yPos += yVel;

        if(yVel <= 0) 
        {
            isFalling = true;
        }

        for(int i = 0; i < objectList.size(); i++) 
        {
            WorldObject temporaryObject = handler.objects.get(i);

            if(temporaryObject.getId() == Identifier.BLOCK) 
            {
                if(getBoundsBottom().intersects(temporaryObject.getBounds())) 
                {
                    isFalling = false;
                }
            }
        }

        if(isFalling || isJumping) 
        {
            yVel += playerGravity;

            if(yVel >= maximumVelocity) 
            {
                yVel = maximumVelocity;
            }
        }
       
        collision(objectList);
    }

    public void render(Graphics graphics) 
    {
    	graphics.setColor(new Color(0, 0, 255));
    	graphics.fillRect((int)xPos, (int)yPos, (int)playerWidth,(int)playerHeight);
    	
    	if(hasKey)
    	{
    		graphics.setColor(new Color(175, 175, 175));
			graphics.fillOval((int)xPos - 7, (int)yPos + 24 + 1, 12, 12);
			
			graphics.setColor(new Color(175, 175, 175));
			
			graphics.fillRect((int)xPos - 2, (int)yPos + 24 + 12, 4, 19);
			graphics.fillRect((int)xPos - 7, (int)yPos + 24 + 16, 7, 3);
			graphics.fillRect((int)xPos - 9, (int)yPos + 24 + 21, 7, 3);
			graphics.fillRect((int)xPos - 6, (int)yPos + 24 + 26, 4, 3);
    	}
    }


    // Gets triggered when the player collides with other objects

    private void collision(LinkedList<WorldObject> objectList) 
    {
        for(int i = 0; i < objectList.size(); i++)
        {
            WorldObject temporaryObject = handler.objects.get(i);
           
        	if(temporaryObject.getSolid() == true) 
            {
                if(getBoundsTop().intersects(temporaryObject.getBounds())) 
                {
                	yPos = temporaryObject.getPosY() + (playerHeight / 2);
                    yVel = 0;
                }

                if(getBoundsBottom().intersects(temporaryObject.getBounds())) 
                {
                    yPos = temporaryObject.getPosY() - playerHeight;
                    yVel = 0;

                    isJumping = false;
                    isFalling = false;
                }

                if(getBoundsRight().intersects(temporaryObject.getBounds()))
                {
                    xPos = temporaryObject.getPosX() - playerWidth;
                }

                if(getBoundsLeft().intersects(temporaryObject.getBounds())) 
                {
                    xPos = temporaryObject.getPosX() + playerWidth;
                }
            } 
            
            else if(temporaryObject.getId() == Identifier.LAVA || temporaryObject.getId() == Identifier.BULLET)
            {
                if(getBounds().intersects(temporaryObject.getBounds())) 
                {
                	soundHandler.loadSound(SoundResource.FAILURE);
                    soundHandler.soundClip.start();
                
                    handler.reloadLevel();

                    return;
                }
            }
            
            else if(temporaryObject.getId() == Identifier.KEY)
            {
                if(getBounds().intersects(temporaryObject.getBounds()) && hasKey == false) 
                {
                	hasKey = true;
                	
                	soundHandler.loadSound(SoundResource.KEY);
                    soundHandler.soundClip.start();

                	handler.removeObject(temporaryObject);
                }
            }
            
            else if(temporaryObject.getId() == Identifier.COIN)
            {
                if(getBounds().intersects(temporaryObject.getBounds())) 
                {
                    Main.levelScore += 25;

                    soundHandler.loadSound(SoundResource.PICKUP);
                    soundHandler.soundClip.start();

                    handler.removeObject(temporaryObject);
                }
            } 
            
            else if(temporaryObject.getId() == Identifier.TRAMPOLINE)
            {
                if(getBoundsBottom().intersects(temporaryObject.getBounds())) 
                {
                	yVel = -27.53F;
                }
                
                else if(getBoundsTop().intersects(temporaryObject.getBounds())) 
                {
                	yVel = 27.53F;
                }
                
                else if(getBoundsRight().intersects(temporaryObject.getBounds()))
                {
                    xPos = temporaryObject.getPosX() - playerWidth;
                }

                else if(getBoundsLeft().intersects(temporaryObject.getBounds())) 
                {
                    xPos = temporaryObject.getPosX() + playerWidth;
                }
                
                if(getBounds().intersects(temporaryObject.getBounds()))
                {
                    soundHandler.loadSound(SoundResource.BOING);
                    soundHandler.soundClip.start();
                }
            } 
            
            else if(temporaryObject.getId() == Identifier.DOOR)
            {
            	if(getBoundsTop().intersects(temporaryObject.getBounds())) 
                {
                	yPos = temporaryObject.getPosY() + (playerHeight / 2);
                    yVel = 0;
                    
                    if(hasKey)
                    {
                    	handler.removeObject(temporaryObject);
                    	hasKey = false;
                    	
                    	soundHandler.loadSound(SoundResource.DOOR);
						soundHandler.soundClip.start();
                    }
                }

                if(getBoundsBottom().intersects(temporaryObject.getBounds())) 
                {
                    yPos = temporaryObject.getPosY() - playerHeight;
                    yVel = 0;

                    isJumping = false;
                    isFalling = false;
                    
                    if(hasKey)
                    {
                    	handler.removeObject(temporaryObject);
                    	hasKey = false;
                    	
                    	soundHandler.loadSound(SoundResource.DOOR);
						soundHandler.soundClip.start();
                    }
                }

                if(getBoundsRight().intersects(temporaryObject.getBounds()))
                {
                    xPos = temporaryObject.getPosX() - playerWidth;
                    
                    if(hasKey)
                    {
                    	handler.removeObject(temporaryObject);
                    	hasKey = false;
                    	
                    	soundHandler.loadSound(SoundResource.DOOR);
						soundHandler.soundClip.start();
                    }
                }

                if(getBoundsLeft().intersects(temporaryObject.getBounds())) 
                {
                    xPos = temporaryObject.getPosX() + playerWidth;
                    
                    if(hasKey)
                    {
                    	handler.removeObject(temporaryObject);
                    	hasKey = false;
                    	
                    	soundHandler.loadSound(SoundResource.DOOR);
						soundHandler.soundClip.start();
                    }
                }
            } 
                        
            else if(temporaryObject.getId() == Identifier.FLAG) 
            {
                if(getBounds().intersects(temporaryObject.getBounds()))
                {
					soundHandler.loadSound(SoundResource.VICTORY);
					soundHandler.soundClip.start();
                    
                	if(Main.user != null) 
                    {
                        saveScore();
                    }

                	try
                	{
	                    handler.clearLevel();
	                	
	            		switch(Main.currentPage) 
	                    {
	                    	case 0: handler.loadLevel(Main.easyLevelPage.get(Main.currentLevel++));
	                    	
	                    	break;
	                    	
	                    	case 1: handler.loadLevel(Main.mediumLevelPage.get(Main.currentLevel++ - 13));
	                    	
	                    	break;
	                    	
	                    	case 2: handler.loadLevel(Main.hardLevelPage.get(Main.currentLevel++ - 25));
	
	                    	break;
	                    }
                	}
                	
                	catch(Exception error)
                	{
                		logger.error(error.getMessage(), error);
                	}
                }
            }
        }
    }


    // Connects to the database and saves your score

    private void saveScore() 
    {
        try 
        {
        	if(Main.user != null)
        	{
        		Main.user.setScore((int)Main.levelScore);
        		Main.user.setLevel((int)Main.currentLevel);
        		
        		accessClient.saveScores(Main.user);
        		
        		int oldScore = 0;
        		
        		if(Main.allScores[Main.currentLevel] != null)
        		{
        			oldScore = Integer.parseInt(Main.allScores[Main.currentLevel]);
        		}
        		
        		if(Main.levelScore > oldScore)
        		{
        			Main.allScores[Main.currentLevel] = Long.toString(Main.levelScore);
            	}
        	}
        } 
        
        catch(Exception error) 
        {
            logger.error(error.getMessage(), error);
        }
    }


    // Used for collision detection

    public Rectangle getBounds() 
    {
        return new Rectangle((int)xPos, (int)yPos, (int)playerWidth, (int)playerHeight);
    }

    public Rectangle getBoundsTop() 
    {
        return new Rectangle((int)xPos + ((int)playerWidth / 4), (int)yPos, (int)playerWidth / 2, (int)playerHeight / 2);
    }

    public Rectangle getBoundsBottom() 
    {
        return new Rectangle((int)xPos + ((int)playerWidth / 4), (int)yPos + ((int)playerHeight / 2), (int)playerWidth / 2, (int)playerHeight / 2);
    }

    public Rectangle getBoundsLeft() 
    {
        return new Rectangle((int)xPos, (int)yPos + 3, 5, (int)playerHeight - 6);
    }

    public Rectangle getBoundsRight() 
    {
        return new Rectangle((int)xPos + ((int)playerWidth - 5), (int)yPos + 3, 5, (int)playerHeight - 6);
    }
    
    public Rectangle getUpdateBounds() 
    {
        return new Rectangle((int)xPos - 64, (int)yPos - 64, (int)playerWidth + 128, (int)playerHeight + 128);
    }
}