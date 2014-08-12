/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Turret.java
 *
 **/

package nl.dalthow.etaron.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import nl.dalthow.etaron.framework.Identifier;
import nl.dalthow.etaron.framework.WorldObject;
import nl.dalthow.etaron.handler.ObjectHandler;
import nl.dalthow.etaron.handler.SoundHandler;
import nl.dalthow.etaron.loader.SoundResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Turret extends WorldObject 
{
    // Declaration

    private int direction;
    private int tickToFire;
    
    private int backFire;
 
    private boolean shouldFire;

    @Autowired
    private ObjectHandler handler;

    @Autowired
    private SoundHandler soundHandler;
    
    private Color detectionColor;


    // Constructor
    
    private Turret(float xPos, float yPos, int direction, Identifier id) 
    {
        super(xPos, yPos, id);

        tickToFire = 45;
        shouldFire = false;

        detectionColor = Color.red;

        this.direction = direction;
    }


    // Gets triggered in the handler and then in the main method
    
    @Override
    public void tick(LinkedList<WorldObject> objectList) 
    {
        for(int i = 0; i < objectList.size(); i++) 
        {
            WorldObject temporaryObject = handler.objects.get(i);

            if(temporaryObject.getId() == Identifier.PLAYER) 
            {
                if(temporaryObject.getBounds().intersects(getVisionLeft()) || temporaryObject.getBounds().intersects(getVisionRight())) 
                {
                    shouldFire = true;

                    if(temporaryObject.getBounds().intersects(getVisionLeft())) 
                    {
                        direction = -1;
                    } 
                    
                    else if(temporaryObject.getBounds().intersects(getVisionRight())) 
                    {
                        direction = 1;
                    }
                } 
                
                else 
                {
                    shouldFire = false;
                }
            }
        }

        if(shouldFire == true) 
        {
            if(tickToFire <= 0) 
            {
                switch(direction) 
                {
                    case -1: handler.addObject(new Projectile(xPos - 24, yPos + 8, (direction * 7.5F), Identifier.LAVA));
                    
                    break;

                    case 1: handler.addObject(new Projectile(xPos + 52, yPos + 8, (direction * 7.5F), Identifier.LAVA));
                        
                    break;
                }

                soundHandler.loadSound(SoundResource.BULLET);
                soundHandler.soundClip.start();
                backFire = 10;
                tickToFire = 45;
            } 
            
            else 
            {
            	if(backFire != 0)
            	{
            		backFire--;
            	}
            	
                tickToFire--;
            }

            detectionColor = Color.red;
        } 
        
        else 
        {
            detectionColor = Color.green;
        }
    }

    @Override
    public void render(Graphics graphicsObject) 
    {
        graphicsObject.setColor(new Color(255, 175, 0));

        graphicsObject.fillRect((int) xPos, (int) yPos, 32, 32);
        graphicsObject.fillRect((int) xPos + 8, (int) yPos + 32, 16, 8);

        switch(direction) 
        {
            case -1: graphicsObject.fillRect((int) xPos - 20 + backFire, (int) yPos + 10, 20, 8);
		               
	                 graphicsObject.setColor(detectionColor);
	                 graphicsObject.fillRect((int) xPos + 20, (int) yPos + 4, 8, (int) (tickToFire / 1.85F));
            break;

            case 1: graphicsObject.fillRect((int) xPos + 32 - backFire, (int) yPos + 10, 20, 8);
               
                	graphicsObject.setColor(detectionColor);
                	graphicsObject.fillRect((int) xPos + 4, (int) yPos + 4, 8, (int) (tickToFire / 1.85F));
            break;
        }
    }


    // Used for collision detection

    @Override
    public Rectangle getBounds()
    {
        return new Rectangle((int) xPos, (int) yPos, 32, 32);
    }


    // Used for vision detection

    public Rectangle getVisionLeft() 
    {
        return new Rectangle((int) xPos - 290, (int) yPos - 128, 288, 216);
    }

    public Rectangle getVisionRight()
    {
        return new Rectangle((int) xPos + 34, (int) yPos - 128, 288, 216);
    }
}